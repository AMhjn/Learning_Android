package com.abhir.myshoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Item(val id:Int,var name:String, var qty:Int?, var isEditing: Boolean)

@Composable
fun MainPage(){
    var shoppingList by remember { mutableStateOf(listOf<Item>()) }
    var showAddNewDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }



    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(8.dp)) {


        Button(onClick = {
            showAddNewDialog = true

        }) {
            Text(text = "Add Item")
        }

        LazyColumn(modifier = Modifier.fillMaxSize()
            .padding(8.dp)) {

            items(shoppingList){
                    item ->
                        if(item.isEditing){
                            ShoppingItemEditor(
                                item = item,
                                onEditComplete = { editedName, editedQuantity ->
                                    shoppingList = shoppingList.map { it.copy(isEditing = false) }
                                    val editedItem = shoppingList.find { it.id == item.id }
                                    editedItem?.let {
                                        it.name = editedName
                                        it.qty = editedQuantity
                                    }
                                }
                            )
                        }
                         else{
                                ShoppingItemsList(
                                    item,
                            onEditFUnction = {
                                shoppingList = shoppingList.map { it -> it.copy(isEditing = item.id == it.id)}
                            },
                            onDeleteFunction = {
                                shoppingList -= item
                            })
                        }

            }
        }
    }


    // To add new item
    if(showAddNewDialog){

        AlertDialog(
            onDismissRequest = {
            showAddNewDialog = false
        },
            title = {
                Column(modifier = Modifier.fillMaxWidth(),) {
                    Text("Add New Item : ", modifier = Modifier.align(Alignment.CenterHorizontally)) }
                },
            confirmButton = {

                Column() {
                    OutlinedTextField(
                        label = {
                            Text("Enter Item Name : ")
                        },
                        modifier = Modifier.padding(8.dp),
                        value = itemName,
                        onValueChange = {
                            itemName = it
                        }
                    )
                    OutlinedTextField(
                        label = {
                            Text("Enter Quantity : ")
                        },
                        modifier = Modifier.padding(8.dp),
                        value = itemQty.toString(),
                        onValueChange = {
                            itemQty = it
                        }
                    )

                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        var newItem = Item(shoppingList.size+1,itemName, itemQty.toIntOrNull()?:1,false)
                        Button(onClick = {
                            shoppingList = shoppingList + newItem
                            itemName =""
                            itemQty = ""
                            showAddNewDialog = false
                        }) {
                            Text("Save")
                        }
                        Button(onClick = {
                            showAddNewDialog = false
                            itemName =""
                            itemQty =""
                        }) {
                            Text("Cancel")
                        }
                    }


                }
            })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingItemsList(item: Item, onEditFUnction: () -> Unit, onDeleteFunction:()->Unit){

    OutlinedCard(onClick = {}, modifier = Modifier.fillMaxWidth()
        .padding(8.dp)) {


        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

            Column() {
                Text("Name : ${item.name}", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp
                ))
                Text("Quantity : ${item.qty}", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
            }


            Row(horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = onEditFUnction,
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                IconButton(
                    onClick = onDeleteFunction,
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }

}

@Composable
fun ShoppingItemEditor(item: Item,onEditComplete: (String, Int) -> Unit) {
    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.qty.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

            BasicTextField(
                value = editedQuantity,
                onValueChange = { editedQuantity = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
        Button(
            onClick = {
                isEditing = false
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
            }
        ) {
            Text("Save")
        }
    }
}


