package com.esoft.paymentsapp.ui.fragment.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.databinding.FragmentLoginBinding

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}