package com.ru.simplemvvm.application.viewmodel.changecolor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ru.simplemvvm.application.data.model.NamedColorModel
import com.ru.simplemvvm.databinding.ColorTileBinding

class ColorsAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<ColorsAdapter.Holder>(), View.OnClickListener {

    var items: List<NamedColorListTile> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onClick(v: View?) {
        val item = v?.tag as? NamedColorModel ?: return
        listener.onColorChosen(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ColorTileBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val namedColor = items[position].namedColor
        val selected = items[position].selected
        with(holder.binding) {
            root.tag = namedColor
            colorNameTextView.text = namedColor.name
            colorView.setBackgroundColor(namedColor.value)
            selectedIndicatorImageView.visibility = if (selected) View.VISIBLE else View.GONE
        }
    }

    class Holder(
        val binding: ColorTileBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onColorChosen(namedColor: NamedColorModel)
    }
}