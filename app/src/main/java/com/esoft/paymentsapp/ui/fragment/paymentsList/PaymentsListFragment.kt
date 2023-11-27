package com.esoft.paymentsapp.ui.fragment.paymentsList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.databinding.FragmentPaymentsListBinding

class PaymentsListFragment: Fragment(R.layout.fragment_payments_list) {

    private var _binding: FragmentPaymentsListBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPaymentsListBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}