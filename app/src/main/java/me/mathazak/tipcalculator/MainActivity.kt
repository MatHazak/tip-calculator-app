package me.mathazak.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import me.mathazak.tipcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateTotal()
        binding.tipSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateTip(progress)
                updateTotal()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.baseCost.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateTotal()
            }
        })
    }

    private fun updateTip(percent: Int) {
        binding.tipPercent.text = getString(R.string.percent, percent)
    }

    private fun updateTotal() {
        val baseAmount = try {
            binding.baseCost.text.toString().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
        val percent = binding.tipSeekBar.progress.toDouble()
        val tipAmount = percent / 100 * baseAmount
        val totalAmount = baseAmount + tipAmount
        binding.tipCost.text = getString(R.string.money, tipAmount)
        binding.totalCost.text = getString(R.string.money, totalAmount)
    }
}