package com.ahsan.composable

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ThemeDropDown(label: String, value: String, list: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ThemeTextField(label = label, value = if(list.isEmpty()) "" else list[0], isReadOnly = true, trailingIcon = R.drawable.ic_bottom_arrow,
        onClick = {
            expanded = true
        }) {

    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        list.forEach {
            DropdownMenuItem(text = { Text(text = it) }, onClick = {
                expanded = false
                onSelected(it)
            })
        }
    }
}