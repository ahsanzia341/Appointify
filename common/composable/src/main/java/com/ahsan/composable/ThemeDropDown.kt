package com.ahsan.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ThemeDropDown(label: String, list: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var value by remember {
        mutableStateOf(if(list.isEmpty()) "" else list[0])
    }
    Column {
        ThemeTextField(label = label, value = value, isReadOnly = true, trailingIcon = R.drawable.ic_bottom_arrow,
            onClick = {
                expanded = true
            }) {
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            list.forEach {
                DropdownMenuItem(text = { Text(text = it) }, onClick = {
                    expanded = false
                    value = it
                    onSelected(it)
                })
            }
        }
    }

}
@Composable
@Preview
fun Preview(){
    ThemeDropDown(label = "Dropdown", list = listOf("Test", "Test1")) {
        
    }
}