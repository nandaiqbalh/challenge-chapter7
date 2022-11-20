package com.nandaiqbalh.themovielisting.presentation.ui.user.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.WorkInfo
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.nandaiqbalh.themovielisting.R
import com.nandaiqbalh.themovielisting.data.local.datasource.UserPreferences
import com.nandaiqbalh.themovielisting.data.network.firebase.model.User
import com.nandaiqbalh.themovielisting.databinding.FragmentProfileBinding
import com.nandaiqbalh.themovielisting.presentation.ui.user.MainActivity
import com.nandaiqbalh.themovielisting.util.workers.KEY_IMAGE_URI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var userDetails: User

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
        viewModel.outputWorkInfos.observe(viewLifecycleOwner, workInfosObserver())
    }

    private fun setOnClickListener() {
        binding.btnUpdateProfile.setOnClickListener {
            navigateToUpdateProfile()
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "update profile")
            analytics.logEvent("click_update", bundle)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.setUserLogin(false)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }

        binding.btnBlurImage.setOnClickListener {
            viewModel.applyBlur(3)
        }
        binding.btnSeeBlur.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                startActivity(actionView)
            } ?: kotlin.run {
                Toast.makeText(requireContext(), "Please add your image profile first!", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCancelBlur.setOnClickListener {
            viewModel.cancelWork()
        }

        binding.ivProfileImage.setOnClickListener {
            Toast.makeText(requireContext(), "Click button update profile to update your profile!", Toast.LENGTH_LONG).show()
            throw RuntimeException("Test Crash")
        }
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }

            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                showWorkFinished()

                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.btnSeeBlur.visibility = View.VISIBLE
                }
            } else {
                showWorkInProgress()
            }
        }
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            btnCancelBlur.visibility = View.VISIBLE
            btnBlurImage.visibility = View.GONE
            btnSeeBlur.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            pbLoading.visibility = View.GONE
            btnCancelBlur.visibility = View.GONE
            btnBlurImage.visibility = View.VISIBLE
        }
    }

    private fun observeData() {
        viewModel.getUserDetail(this@ProfileFragment)
    }

    fun bindDataToView(user: User) {
        userDetails = user
        user.let {
            binding.apply {
                tvUsername.text = user.username
                tvEmail.text = user.email
                tvFullName.text = user.fullName
                tvDateOfBirth.text = user.dateOfBirth
                tvAddress.text = user.address

                user.profileImage.let {
                    if (it.isEmpty().not()) {
                        Glide.with(this@ProfileFragment)
                            .load(convertStringToBitmap(it))
                            .into(binding.ivProfileImage)
                        viewModel.getImageUri(requireActivity(), convertStringToBitmap(it))
                    }
                }
            }
        }
    }

    private fun convertStringToBitmap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun navigateToUpdateProfile() {
        val options = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, false)
            .build()
        val bundle = Bundle()
        bundle.putParcelable(USER_DETAILS, userDetails)
        findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment, bundle, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val USER_DETAILS = "user_details"
    }
}