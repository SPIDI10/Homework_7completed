package com.alsam.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alsam.criminalintent.databinding.ListItemCrimeBinding

class CrimeListAdapter(private val crimes: List<Crime>, private val onCrimeClicked: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    private val VIEW_TYPE_NORMAL = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)

        return when (viewType) {
            VIEW_TYPE_NORMAL -> NormalCrimeHolder(binding)

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]

        when (holder) {
            is NormalCrimeHolder -> holder.bind(crime)

        }
    }

    override fun getItemViewType(position: Int): Int {
        val crime = crimes[position]

        return when {

            else -> VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int = crimes.size

    inner class NormalCrimeHolder(private val binding: ListItemCrimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()
            binding.root.setOnClickListener {

                onCrimeClicked()
            }

            binding.crimeSolved.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
