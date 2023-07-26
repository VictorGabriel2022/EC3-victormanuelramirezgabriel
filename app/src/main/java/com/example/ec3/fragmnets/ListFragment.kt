package com.example.ec3.fragmnets
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ec3.CardAdapter
import com.example.ec3.R
import com.example.ec3.model.CardDetails
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        cardAdapter = CardAdapter(emptyList())
        recyclerView.adapter = cardAdapter


        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://db.ygoprodeck.com/api/v7/cardinfo.php")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()
                    val cardDetails = Gson().fromJson(json, CardDetails::class.java)
                    val cards = cardDetails.data

                    activity?.runOnUiThread {
                        cardAdapter.setData(cards)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })

        return view
    }
}
