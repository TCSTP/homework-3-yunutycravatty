package tcs.app.dev.homework1.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tcs.app.dev.homework1.data.Cart
import tcs.app.dev.homework1.data.Discount
import tcs.app.dev.homework1.data.MockData

@Composable
fun DiscountTab(
    discounts: List<Discount>,
    cart: Cart,
    modifier: Modifier,
    onAddDiscount: (Discount) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(9.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        items(
            items = discounts,
            key = { it.hashCode() }
        ) { discount ->
            val alreadyInCart = discount in cart.discounts
            DiscountRow(
                discount = discount,
                enabled = !alreadyInCart,
                onAdd = { onAddDiscount(discount) }
            )
        }
    }
}

@Composable
private fun DiscountRow(
    discount: Discount,
    enabled: Boolean,
    onAdd: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocalOffer,
                contentDescription = "Discount"
            )
        }
        Spacer(modifier = Modifier.width(9.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = discountDescription(discount))
        }
        Button(
            enabled = enabled,
            onClick = onAdd
        ) {
            Text("Add") //TODO: stringResource(...)
        }
    }
}


@Composable
fun discountDescription(discount: Discount): String =
    when (discount) {
        is Discount.Percentage -> "${discount.value}% off!"
        is Discount.Fixed -> "-${discount.amount}"
        is Discount.Bundle -> "Buy ${discount.amountItemsGet} ${
            stringResource(
                MockData.getName(
                    discount.item
                )
            )
        }, pay ${discount.amountItemsPay}!"
    }