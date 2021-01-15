package com.project.travelmedrivers.ui.openTravels

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel
import com.project.travelmedrivers.utils.Util


class OpenTravelArrayAdapter(
    var travelList: List<Travel?>,
    var viewModel: MainViewModel, var markerNewTravel: SharedPreferences
) :
    RecyclerView.Adapter<OpenTravelArrayAdapter.ViewHolder>() {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    // get the size of the list
    override fun getItemCount(): Int {
        return travelList.size ?: 0
    }

    // specify the row layout file and click for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.open_item_lv, parent, false)
        return ViewHolder(view, parent)
    }

    // load data in each row element
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("RestrictedApi", "CommitPrefEdits", "WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val source = holder.source
        val destination = holder.destination
        val date = holder.date
        val email = holder.email
        val phone = holder.phone
        val name = holder.name
        val passenger = holder.passenger
        val newTravel = holder.tvNewTravel
        holder.travel = travelList[listPosition]!!
        source.text = travelList[listPosition]!!.sourceAdders
        destination.text = travelList[listPosition]!!.destinationAddress[0]
        date.text = travelList[listPosition]!!.departureDate
        passenger.text = travelList[listPosition]!!.passengers.toString()
        //  email.text = travelList[listPosition]!!.email
        //  phone.text = "0" + travelList[listPosition]!!.phoneNumber.toString()
        name.text = travelList[listPosition]!!.name
        if (markerNewTravel.getBoolean(holder.travel.id, false))
            newTravel.visibility = View.VISIBLE
        if (travelList[listPosition]!!.serviceProvider.containsKey(FirebaseAuth.getInstance().currentUser!!.email?.let {
                Util.emailToKey(it)
            }))
            holder.cbIsOfferSent.isChecked = true
    }

    @SuppressLint("RestrictedApi")
    inner class ViewHolder(itemView: View, parent: ViewGroup) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var source: TextView
        var destination: TextView
        var date: TextView
        var passenger: TextView
        var email: FloatingActionButton
        var phone: FloatingActionButton
        var name: TextView
        var whatsApp: ImageButton
        var bSendOffer: Button
        var btMap: MaterialButton
        var tvNewTravel: TextView
        var cbIsOfferSent: CheckBox
        lateinit var travel: Travel

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSource) as TextView
            destination = itemView.findViewById(R.id.tvDestination) as TextView
            date = itemView.findViewById(R.id.tvDate) as TextView
            passenger = itemView.findViewById(R.id.tvPassenger)
            name = itemView.findViewById(R.id.tvName)
            email = itemView.findViewById(R.id.bSendEmail)
            email.setOnClickListener {

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(travel.email))
                    putExtra(Intent.EXTRA_SUBJECT, "offer")
                    putExtra(
                        Intent.EXTRA_TEXT, "Hi,I would like to offer you travel service " +
                                "for your request from Source address ${travel.sourceAdders} to ${travel.destinationAddress[0]}"
                    )
                    data = Uri.parse("mailto:")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                    getApplicationContext().startActivity(intent)
            }
            phone = itemView.findViewById(R.id.bPhoneCall)
            phone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${travel.phoneNumber}")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                    getApplicationContext().startActivity(intent)
            }
            whatsApp = itemView.findViewById(R.id.bWhatsApp)
            whatsApp.setOnClickListener {

                val sendIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse ( "https://wa.me/${travel.phoneNumber}/?text=" + "Hi i want to offer you travel" )
                    setPackage("com.whatsapp");
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (sendIntent.resolveActivity(getApplicationContext().packageManager) != null) {
                    getApplicationContext().startActivity(sendIntent)
                } else
                    Toast.makeText(getApplicationContext(), "No App Found", Toast.LENGTH_LONG)
                        .show()

            }
            bSendOffer = itemView.findViewById(R.id.bSendOffer)
            bSendOffer.setOnClickListener {
                val key = FirebaseAuth.getInstance().currentUser?.email?.let { it1 ->
                    Util.emailToKey(it1)
                }
                if (key != null) {
                    this@ViewHolder.travel.serviceProvider[key] = false
                }
                viewModel.updateTravel(this.travel)
            }
            btMap = itemView.findViewById(R.id.btMap)
            btMap.setOnClickListener { OpenInGooleMaps(travel) }
            tvNewTravel = itemView.findViewById(R.id.tvNewTravel)
            cbIsOfferSent = itemView.findViewById(R.id.cbIsOfferSent)
        }

        override fun onClick(v: View?) {
            if (email.visibility == View.GONE) {
                email.visibility = View.VISIBLE
                phone.visibility = View.VISIBLE
                whatsApp.visibility = View.VISIBLE
                name.visibility = View.VISIBLE
                btMap.visibility = View.VISIBLE
                itemView.findViewById<ImageView>(R.id.imageView).rotation = 180F
            } else {
                btMap.visibility = View.GONE
                email.visibility = View.GONE
                phone.visibility = View.GONE
                whatsApp.visibility = View.GONE
                name.visibility = View.GONE
                itemView.findViewById<ImageView>(R.id.imageView).rotation = 0F
            }
        }
        @SuppressLint("RestrictedApi")
        fun OpenInGooleMaps(travel: Travel) {
            // Space+Needle+Seattle+WAPike+Place+Market+Seattle+WA&travelmode=bicycling"

            val origin = "https://www.google.com/maps/dir/?api=1&origin=" + travel.sourceAdders +
            "&destination=" + travel.sourceAdders

            var wayP = "&waypoints="+travel.destinationAddress[0]
            for (i in 1 until  travel.destinationAddress.size) wayP += "|" + travel.destinationAddress[i]
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("$origin$wayP&travelmode=driving"))
            getApplicationContext().startActivity(browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

}




