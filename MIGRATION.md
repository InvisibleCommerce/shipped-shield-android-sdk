# Migration Guide

## Installation

Replace the line in your `build.gradle`

```
dependencies {
    implementation 'com.invisiblecommerce:shippedshield:0.1.2'
}
```

With

```
dependencies {
    implementation 'com.invisiblecommerce:shippedsuite:0.1.0'
}
```

## Setup

Replace

```kotlin
ShippedShield.configurePublicKey(
    this,
    "Public key"
)

ShippedShield.setMode(Mode.PRODUCTION)
```

With

```kotlin
ShippedSuite.configurePublicKey(
    this,
    "Public key"
)

ShippedSuite.setMode(Mode.PRODUCTION)
```

### Create a Shield Widget view

Replace

```xml
<com.invisiblecommerce.shippedshield.widget.WidgetView
    android:id="@+id/widget_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/order_value" />
```

```kotlin
binding.widgetView.updateOrderValue(orderValue)
```

With

```xml
<com.invisiblecommerce.shippedsuite.widget.WidgetView
    android:id="@+id/widget_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="8dp"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/order_value" />
```

```kotlin
binding.widgetView.type = ShippedSuiteType.GREEN
binding.widgetView.isRespectServer = true

binding.widgetView.updateOrderValue(orderValue)
```

### Customization

- Send the Shield Fee request

Replace

```kotlin
ShippedShield().getShieldFee(
    orderValue,
    object : ShippedShield.Listener<ShieldOffer> {
        override fun onSuccess(response: ShieldOffer) {
            shieldLiveData.value = ShieldOfferStatus.Success(response)
        }

        override fun onFailed(exception: ShieldException) {
            shieldLiveData.value = ShieldOfferStatus.Fail(exception)
        }
    }
)
```

With

```kotlin
ShippedSuite().getOffersFee(
    orderValue,
    object : ShippedSuite.Listener<ShippedOffers> {
        override fun onSuccess(response: ShippedOffers) {
            shippedLiveData.value = ShippedOffersStatus.Success(response)
        }

        override fun onFailed(exception: ShippedException) {
            shippedLiveData.value = ShippedOffersStatus.Fail(exception)
        }
    }
)
```

- Display learn more modal

Replace

```kotlin
LearnMoreDialog.show(requireContext())
```

With

```kotlin
LearnMoreDialog.show(requireContext(), ShippedSuiteType.GREEN_AND_SHIELD)
```