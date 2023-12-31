package com.example.oncash.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oncash.Component.withdrawalTransaction_RecylerViewAdapter
import com.example.oncash.DataType.withdrawalTransaction
import com.example.oncash.R
import com.example.oncash.ViewModel.home_viewModel
import com.example.oncash.ViewModel.wallet_viewModel
import com.example.oncash.databinding.ActivityWalletBinding
import com.example.oncash.databinding.FragmentProfileFragmentBinding
import com.example.oncash.databinding.FragmentRedeemBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [redeem.newInstance] factory method to
 * create an instance of this fragment.
 */
class redeem : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var  binding : FragmentRedeemBinding
    private var userNumber: Long = 0
    private var userRecordId: String? = null
    var walletBalance = 0
    private val viewModel: wallet_viewModel by viewModels()
    lateinit var homeViewmodel :home_viewModel
    private val adapter = withdrawalTransaction_RecylerViewAdapter()
    private var withdrawalList :ArrayList<withdrawalTransaction> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRedeemBinding.inflate(inflater , container , false )
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var userRecordId :String
        homeViewmodel = activity.run{
            ViewModelProvider(this!!).get(home_viewModel::class.java)
        }

        homeViewmodel.getWalletPrice().observe(viewLifecycleOwner) {
                walletBalance = it.currentBal
            binding.walletBala.text = walletBalance.toString()

        }

            homeViewmodel.getuserData().observe(viewLifecycleOwner){
                userRecordId = it.userRecordId
                userNumber = it.userNumber
            }

            binding.withdrawalTransaction.adapter = adapter
            binding.withdrawalTransaction.layoutManager = LinearLayoutManager(view.context , LinearLayoutManager.VERTICAL ,false)

            lifecycleScope.launch { getTransaction() }

            binding.withdrawButton.setOnClickListener {
                val requestAmount = binding.withdrawRequestedAmount.text.toString()
                if (requestAmount.isNotEmpty()) {
                    if (walletBalance >= requestAmount.toInt()) {
                        if (requestAmount.toInt() > 20) {

                            viewModel.withdrawRequest(
                                userNumber,
                                requestAmount.toInt(),
                                walletBalance,
                                userRecordId,
                            )
                            viewModel.getWithdrawalRequest().observe(viewLifecycleOwner) { status ->
                                if (status.response.contains("200")) {
                                    // viewModel.getWallet(userRecordId)
                                    //viewModel.getWalletPrice().observe(this, Observer { wallet ->

                                    //    walletBalance = wallet
                                    walletBalance -= status.withdrawalTransaction.WithdrawalAmount.toInt()
                                    binding.walletBala.text = walletBalance.toString()
                                    binding.withdrawRequestedAmount.editableText.clear()

                                    withdrawalList.add(status.withdrawalTransaction)
                                    adapter.updateList(withdrawalList)

                                    Snackbar.make(
                                        binding.root,
                                        "Withdraw Successful",
                                        Snackbar.LENGTH_LONG
                                    ).show()

                                }

                            }

                        } else {
                            Snackbar.make(
                                binding.root,
                                "Requested Amount Should Be More Then 20 Rs",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Insufficient balance",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Snackbar.make(binding.root, "Please enter withdraw amount", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }


        private  fun getTransaction(){
            homeViewmodel.getWithdrawalTransaction().observe(viewLifecycleOwner , Observer { withdrawalTransaction ->
                withdrawalList = withdrawalTransaction
                adapter.updateList(withdrawalTransaction)
            })
        }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment redeem.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            redeem().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}