package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentLoginBinding
import com.websarva.wings.android.flat.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var input: LoginViewModel.LoginInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogin.setOnSafeClickListener {
                input = LoginViewModel.LoginInput(
                    name = etInputNickname.text.toString(),
                    password = etInputPassword.text.toString(),
                    isNameOk = false,
                    isPasswordOk = false
                )
                viewModel.checkAndTrim(input)
            }
        }

        viewModel.checkResult.observe(viewLifecycleOwner, {
            input = it
            if (it.isNameOk) {
                binding.tilInputNickname.apply {
                    isErrorEnabled = false
                }
            }
            else if (!it.isNameOk) {
                binding.tilInputNickname.apply {
                    error = getString(R.string.input_nickname_error)
                    isErrorEnabled = true
                }
            }
            if (it.isPasswordOk) {
                binding.tilInputPassword.apply {
                    isErrorEnabled = false
                }
            }
            else if (!it.isPasswordOk) {
                binding.tilInputPassword.apply {
                    error = getString(R.string.input_password_length_error)
                    isErrorEnabled = true
                }
            }
            if (it.isNameOk && it.isPasswordOk) {
                //TODO: is_loggedinのPOST
                Log.d("login", "loginOk")
            }
        })

        //TODO: is_loggedinの結果を監視し、ダイアログ表示orログイン

        //TODO: loginの結果を監視し、エラー表示orルームに格納

        //TODO: ルームに格納できたのを確認し、画面遷移

        binding.tvToAccountRegistration.setOnSafeClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToAccountRegistrationFragment()
            view.findNavController().navigate(action)
        }
    }
}