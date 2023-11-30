package com.esoft.paymentsapp.ui.fragment.paymentsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.databinding.PaymentListItemBinding
import com.esoft.paymentsapp.model.PaymentItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAmount

class PaymentsListAdapter: RecyclerView.Adapter<PaymentsListAdapter.PaymentsViewHolder>() {

    private var items: List<PaymentItem> = listOf()

    fun updateItemList(list: List<PaymentItem>) {
        items = list
        notifyItemChanged(0, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_list_item, parent, false)
        return PaymentsViewHolder(view = view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PaymentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PaymentListItemBinding.bind(view)

        fun bind(item: PaymentItem) = with(binding) {

            textPaymentId.text = item.id.toString()
            textPaymentAmount.text = adjustAmount(item.amount)
            textPaymentTitle.text = item.title

            val date = getDateByMls(item.created)
            if (date.isEmpty()) {
                textPaymentDate.visibility = View.GONE
            } else {
                textPaymentDate.visibility = View.VISIBLE
                textPaymentDate.text = date
            }

        }

        private fun adjustAmount(amount: String?) : String {
           return when {
                amount.isNullOrEmpty() -> return ""
                else -> "%.2f".format(amount.toFloat())
            }
        }

        private fun getDateByMls(mls: String?) : String {
            if (mls.isNullOrEmpty()) return ""
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            val instant = Instant.ofEpochMilli(mls.toLong())
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            return formatter.format(date)
        }

    }



}