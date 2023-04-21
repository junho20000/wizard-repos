package com.weather.fitpet.implementation.weatherlist

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weather.fitpet.databinding.ItemWeatherBinding
import com.weather.fitpet.implementation.weather.data.ShowWeatherData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/******************************************************************************
 * 업 무 명    : WeatherAdapter
 * 파 일 명    : com.weather.fitpet.implementation.weatherlist.WeatherAdapter
 * 설 명      : WeatherAdapterSheet 의 list adapter
 ************************** 변 경 이 력 ****************************************
 * 번호  작 업 자    작  업  일            변경내용
 * *****************************************************************************
 *  1    노준호      2023-04-20           최초작성
 ******************************************************************************/

class WeatherAdapter(
    private var items: LiveData<ArrayList<ShowWeatherData>>,
    private var context: Context

) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    var recyclerViewItems = ArrayList<ShowWeatherData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        items.value?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return items.value?.size!!
    }

    fun setData(newRecyclerViewItems: ArrayList<ShowWeatherData>){
        val diffCallback = DiffCallback(recyclerViewItems, newRecyclerViewItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        recyclerViewItems.clear()
        recyclerViewItems.addAll(newRecyclerViewItems)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun setIcon(id: Int): Int {
        var iconRes: Int

        when (id) {
            in 200..299 -> iconRes = context.getResources().getIdentifier("wi_day_sleet_storm", "drawable", context.getPackageName())
            in 300..399 -> iconRes = context.getResources().getIdentifier("wi_sleet", "drawable", context.getPackageName())
            in 500..599 -> iconRes = context.getResources().getIdentifier( "wi_rain", "drawable", context.getPackageName())
            in 600..699 -> iconRes = context.getResources().getIdentifier( "wi_snow", "drawable", context.getPackageName())
            in 700..799 -> iconRes = context.getResources().getIdentifier("wi_smoke", "drawable", context.getPackageName())
            800 -> iconRes = context.getResources().getIdentifier("wi_day_sunny", "drawable", context.getPackageName())
            in 801..899 -> iconRes = context.getResources().getIdentifier("wi_day_cloudy", "drawable", context.getPackageName())
            else -> iconRes = context.getResources().getIdentifier("wi_horizon", "drawable", context.getPackageName())
        }

        return iconRes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(date: String): String {
        var dspDate = ""
        var parsedToken =  date.split(' ')
        var parsedDate = parsedToken[0]
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDateTime.now().format(formatter)

        dspDate = if (parsedDate == currentDate)
            "Today"
        else {
            parsedDate
        }

        return dspDate
    }

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(recyclerViewItem: ShowWeatherData) = with(binding) {
            item = recyclerViewItem

            val iconId = setIcon(item!!.id)
            if (iconId != 0)
                binding.weatherIcon.setImageResource(iconId)

            val dspDate = setData(item!!.dt_txt)
            binding.weatherDate.text = dspDate
        }
    }

    inner class DiffCallback(
        private var oldList: ArrayList<ShowWeatherData>,
        private var newList: ArrayList<ShowWeatherData>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
