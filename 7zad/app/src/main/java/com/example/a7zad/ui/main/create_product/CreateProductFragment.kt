package com.example.a7zad.ui.main.create_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a7zad.R
import com.example.a7zad.model.product.Product
import com.example.a7zad.ui.main.PageViewModel
import kotlinx.coroutines.runBlocking


class CreateProductFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    companion object {
        fun newInstance() = CreateProductFragment()
    }

    lateinit var viewModel: PageViewModel

    private lateinit var btn: Button
    private lateinit var nameTextField: EditText
    private lateinit var descTextField: EditText
    private lateinit var priceTextField: EditText
    private lateinit var categorySpinner: Spinner


    private  var categoryId: Int =10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        viewModel.getCategories()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_create_product, container, false)

        btn = rootView.findViewById(R.id.btnCreateProduct)
        nameTextField = rootView.findViewById(R.id.name)
        descTextField = rootView.findViewById(R.id.description)
        priceTextField = rootView.findViewById(R.id.price)
        categorySpinner = rootView.findViewById(R.id.category)

        categorySpinner.adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown,viewModel.categories.value!! )

        categorySpinner.onItemSelectedListener = this

        viewModel.categories.observe(this.viewLifecycleOwner){
            categorySpinner.adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown,it )
        }

        btn.setOnClickListener {
            val name = nameTextField.text.toString()
            val desc = descTextField.text.toString()
            val price = priceTextField.text.toString()
            val fields = listOf(name, desc, price)
            if(fields.any { it.isEmpty() }){
                Toast.makeText(requireContext(), "Pola nie mogą być puste!!",LENGTH_LONG).show()

            }else{
                val createdProduct = Product(categoryId,name, desc,price.toDouble())
                runBlocking {
                    val response = viewModel.api.createProduct(createdProduct)
                    if(response.isSuccessful()){
                        Toast.makeText(requireContext(), "Udało Ci sie stworzyć produkt",LENGTH_LONG).show()
                        viewModel.getProducts()
                    }else{
                        Toast.makeText(requireContext(), "Ups, coś poszło nie tak",LENGTH_LONG).show()
                    }
                }
            }
        }

        return rootView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val category = viewModel.categories.value!![p2]
        categoryId = category.id

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}