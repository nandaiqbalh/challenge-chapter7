package com.nandaiqbalh.themovielisting.presentation.ui.user.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nandaiqbalh.themovielisting.R
import com.nandaiqbalh.themovielisting.data.local.preference.UserPreferences
import com.nandaiqbalh.themovielisting.databinding.FragmentProfileBinding
import com.nandaiqbalh.themovielisting.di.UserServiceLocator
import com.nandaiqbalh.themovielisting.presentation.ui.user.MainActivity
import com.nandaiqbalh.themovielisting.util.viewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(UserServiceLocator.provideUserRepository(requireContext()))
    }

    private val args: ProfileFragmentArgs by navArgs()

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
    }

    private fun setOnClickListener() {
        binding.btnUpdateProfile.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.profileFragment, false)
                .build()
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment, null, options)        }

        binding.btnLogout.setOnClickListener {
            viewModel.setUserLogin(false)
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }
    }

    private fun observeData() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            bindDataToView(it)
        }
    }

    private fun bindDataToView(user: UserPreferences?) {
        user?.let {
            binding.apply {
                tvUsername.text = user.username
                tvEmail.text = user.email
                tvFullName.text = user.fullName
                tvDateOfBirth.text = user.dateOfBirth
                tvAddress.text = user.address

                user.profileImage?.let {
                    if (it.isEmpty().not()) {
                        Glide.with(this@ProfileFragment)
                            .load(convertStringToBitmap(it))
                            .into(binding.ivProfileImage)
                    }
                }

            }
        }
    }

    private fun convertStringToBitmap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}