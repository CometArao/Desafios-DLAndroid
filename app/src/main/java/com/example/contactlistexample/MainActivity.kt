package com.example.contactlistexample

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    // Añadimos los elementos del formulario
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var isAvailable: CheckBox
    private lateinit var buttonAddContact: Button
    private lateinit var switchFilter : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vinculamos los elementos - Form elements find
        etName = findViewById<EditText>(R.id.etName)
        etPhone = findViewById<EditText>(R.id.etPhone)
        isAvailable = findViewById(R.id.checkBox)
        buttonAddContact = findViewById(R.id.button)


        switchFilter = findViewById(R.id.switch_available) // Inicializar el Switch

        setRecyclerViewAdapter(contactList)

        switchFilter.setOnCheckedChangeListener { _, isChecked ->
            filterContacts(isChecked) // Llama a la función de filtrado según el estado del Switch
        }

        buttonAddContact.setOnClickListener{
            addContact()
        }




    }

    private fun addContact() {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val isAvailable = isAvailable.isChecked

        // Validamos que los campos no estén vacíos
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Creamos el objeto tipo Contact
        val contact = Contact(name, phone, isAvailable)
        contactList.add(contact)

        adapter.notifyDataSetChanged()

        // Limpiamos los campos
        etName.text.clear()
        etPhone.text.clear()

    }

    private fun filterContacts(isAvailableOnly: Boolean) {
        val filteredList = if (isAvailableOnly){
            contactList.filter { it.isAvailable }
        } else {
            contactList
        }

        adapter.updateContacts(filteredList)
    }

    // RecyclerView
    private fun setRecyclerViewAdapter(contactList: List<Contact>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ContactAdapter(contactList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}