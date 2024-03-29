# ShippedShield Android SDK (Deprecated)

 [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
 [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
 [![GitHub release](https://img.shields.io/github/release/InvisibleCommerce/shipped-shield-android-sdk.svg)](https://github.com/InvisibleCommerce/shipped-shield-android-sdk/releases)
 [![MavenCentral](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fservice%2Flocal%2Frepositories%2Freleases%2Fcontent%2Fcom%2Finvisiblecommerce%2Fshippedshield%2Fmaven-metadata.xml)](https://s01.oss.sonatype.org/#nexus-search;quick~shippedshield)
 [![JitPack](https://www.jitpack.io/v/InvisibleCommerce/shipped-shield-android-sdk.svg)](https://www.jitpack.io/#InvisibleCommerce/shipped-shield-android-sdk)
 [![License](https://img.shields.io/badge/license-MIT%20License-00AAAA.svg)](https://github.com/InvisibleCommerce/shipped-shield-android-sdk/blob/main/LICENSE)
 [![Codecov](https://codecov.io/gh/InvisibleCommerce/shipped-shield-android-sdk/branch/main/graph/badge.svg?token=SuRuzq1Bx0)](https://codecov.io/gh/InvisibleCommerce/shipped-shield-android-sdk)

**Note: This version of the SDK is deprecated in favor of our [Suite SDK](https://github.com/InvisibleCommerce/shipped-suite-android-client-sdk). See the [MIGRATION](MIGRATION.md)**

Shipped Shield offers premium package assurance for shipments that are lost, damaged or stolen. Instantly track and resolve shipment issues hassle-free with the app.

## Example

To run the example project, clone the repo, and open the project from [Android Studio](https://developer.android.com/studio).

## Requirements

* Android 5.0 (API level 21) and above

## Installation

Add `shippedshield` to your `build.gradle` dependencies.

```
dependencies {
    implementation 'com.invisiblecommerce:shippedshield:0.1.2'
}
```

## Setup

Configure the SDK with your ShippedShield publishable API key.

```kotlin
ShippedShield.configurePublicKey(
    this,
    "Public key"
)
```

If you want to test on different endpoint, you can customize mode. The default is Development mode, so make sure to switch to Production mode for your production build. 

```kotlin
ShippedShield.setMode(Mode.PRODUCTION)
```

### Create a Shield Widget view

You can put it in the layout file where you want.

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

Whenever the cart value changes, update the widget view with the latest cart value. This value should be the sum of the value of the order items, prior to discounts, shipping, taxes, etc. It will request shipped fee automatically.

```kotlin
binding.widgetView.updateOrderValue(orderValue)
```

To get the callback from widget, you need implement the `WidgetView.Callback`.

```kotlin
binding.widgetView.callback = object : WidgetView.Callback<BigDecimal> {
    override fun onResult(result: Map<String, Any>) {
        Log.d(TAG, "Widget response $result")
    }
}
```

```
Widget response {shieldFee=2.27, isShieldEnabled=false}
```

Within the callback, implement any logic necessary to add or remove Shield from the cart, based on whether `isShieldEnabled` is true or false. 

### Customization

If you plan to implement the widget yourself to fit the app style, you can still use the functionality provided by the SDK.

- Send the Shield Fee request

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

- Display learn more modal

```kotlin
LearnMoreDialog.show(requireContext())
```

## License

ShippedShield is available under the MIT license. See the [LICENSE](LICENSE) file for more info.
