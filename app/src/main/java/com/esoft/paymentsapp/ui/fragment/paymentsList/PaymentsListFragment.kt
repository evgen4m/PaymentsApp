package com.esoft.paymentsapp.ui.fragment.paymentsList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.app.PaymentsApp
import com.esoft.paymentsapp.databinding.FragmentPaymentsListBinding
import com.esoft.paymentsapp.model.PaymentItem

class PaymentsListFragment: Fragment(R.layout.fragment_payments_list) {

    private var _binding: FragmentPaymentsListBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<PaymentsListViewModel> {
        val paymentsRepository = (requireActivity().application as PaymentsApp).paymentsRepository
        val authRepository = (requireActivity().application as PaymentsApp).authRepository
        PaymentsListViewModelFactory(
            paymentsRepository = paymentsRepository,
            authRepository = authRepository
        )
    }

    private var adapter: PaymentsListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPaymentsListBinding.bind(view)
        adapter = PaymentsListAdapter()
        binding.paymentsListView.adapter = adapter

        observeViewModel()
        initViews()
    }

    private fun initViews() = with(binding) {
        refreshPayments.setOnRefreshListener { viewModel.loadPaymentsItems() }
        logoutMainView.setOnClickListener { viewModel.logout() }
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ::updateUi)
    }

    private fun updateUi(paymentsState: PaymentsState) {
        when(paymentsState) {
            is PaymentsState.Loading -> showLoading()
            is PaymentsState.Error -> showErrorMsg(message = paymentsState.error)
            is PaymentsState.Success -> showItems(items = paymentsState.data)
            is PaymentsState.ExitScreen -> { navigateToLoginScreen() }
        }
    }

    private fun navigateToLoginScreen() {
        findNavController().navigate(R.id.loginFragment, null, navOptions {
            popUpTo(R.id.paymentsListFragment) { inclusive = true }
        })
    }

    private fun showItems(items: List<PaymentItem>) {
        binding.refreshPayments.isRefreshing = false
        adapter?.updateItemList(list = items)
    }

    private fun showErrorMsg(message: String) {
        binding.refreshPayments.isRefreshing = false
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        binding.refreshPayments.isRefreshing = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }

}