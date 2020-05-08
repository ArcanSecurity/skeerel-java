# Skeerel JAVA

A Java library for the Skeerel API [https://doc.skeerel.com](https://doc.skeerel.com)

# Requirements

Minimum java version: 8

# Install

### Via Gradle

Add this dependency to your project's build file:

```groovy
implementation "com.arcansecurity.skeerel:skeerel-java:2.0.0"
```

### Via Maven

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.arcansecurity.skeerel</groupId>
  <artifactId>skeerel-java</artifactId>
  <version>2.0.0</version>
</dependency>
```

# Usage

### Dealing with delivery method

When a user pays with Skeerel, it's likely that you will have to ship its order.
In that case, just set the url that Skeerel should call to get your delivery methods 
in your `data-delivery-methods-url` parameter. For instance:
```
https://site.com/path/to/delivery/methods_
https://site.com/path/to/delivery/methods?uid=__USER___
https://site.com/path/to/delivery/methods?uid=__USER__&zip_code=__ZIP_CODE&city=__CITY__&country=__COUNTRY__
https://site.com/path/to/delivery/methods?some_var=some_value&zip_code=__ZIP_CODE&city=__CITY__&country=__COUNTRY__
```

You can personalize as you like your url, so you can send accurate shipping pricing.

Note that `__USER__` (user identifier), `__ZIP_CODE__`, `__CITY__`, `__COUNTRY__` (two letters country code) are generics values that 
will be replaced automatically before calling your page.

In case of a *guest checkout*, `__USER__` value will be set to empty string.

To format delivery methods, we recommend that you use our embedded method:
```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.data.address.Country;
import com.arcansecurity.skeerel.data.delivery.*;

public class Main {

    public static void main(String[] args) {
        // A standard delivery mode
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        deliveryMethod.setId("standard");
        deliveryMethod.setType(Type.HOME);
        deliveryMethod.setPrimary(true);
        deliveryMethod.setName("Standard shipping");
        deliveryMethod.setDeliveryTextContent("delivery in 3 days");
        deliveryMethod.setPrice(499);



        // But also a pick up mode
        DeliveryMethod deliveryMethodRelay = new DeliveryMethod();
        deliveryMethodRelay.setId("my_relay");
        deliveryMethodRelay.setType(Type.RELAY);
        deliveryMethodRelay.setName("Pick-up & go");
        deliveryMethodRelay.setDeliveryTextContent("Deliver on ");
        deliveryMethodRelay.setPrice(299);

        // Pick up points
        PickUpPoint pickUpPoint1 = new PickUpPoint();
        pickUpPoint1.setId("1");
        pickUpPoint1.setName("Pick-up 1");
        pickUpPoint1.setAddress("Address 1");
        pickUpPoint1.setZipCode("zip");
        pickUpPoint1.setCity("city");
        pickUpPoint1.setCountry(Country.fromAlpha2("FR"));
        pickUpPoint1.setDeliveryTextContent("tomorrow");
        pickUpPoint1.setDeliveryTextColor(Color.GREEN);
        pickUpPoint1.setPrice(399);
        pickUpPoint1.setPriceTextColor(Color.RED);

        PickUpPoint pickUpPoint2 = new PickUpPoint();
        pickUpPoint2.setId("2");
        pickUpPoint2.setPrimary(true);
        pickUpPoint2.setName("Pick-up 2");
        pickUpPoint2.setAddress("address 2");
        pickUpPoint2.setZipCode("zip");
        pickUpPoint2.setCity("city");
        pickUpPoint2.setCountry(Country.fromAlpha2("FR"));

        PickUpPoint pickUpPoint3 = new PickUpPoint();
        pickUpPoint3.setId("3");
        pickUpPoint3.setName("Pick-up 3");
        pickUpPoint3.setAddress("Address 3");
        pickUpPoint3.setZipCode("zip");
        pickUpPoint3.setCity("city");
        pickUpPoint3.setCountry(Country.fromAlpha2("FR"));

        PickUpPoints pickUpPointsRelay = new PickUpPoints();
        pickUpPointsRelay.add(pickUpPoint1);
        pickUpPointsRelay.add(pickUpPoint2);
        pickUpPointsRelay.add(pickUpPoint3);

        deliveryMethodRelay.setPickUpPoints(pickUpPointsRelay);



        // And why not getting the order directly in the store
        DeliveryMethod deliveryMethodCollect = new DeliveryMethod();
        deliveryMethodCollect.setId("store_collect");
        deliveryMethodCollect.setType(Type.COLLECT);
        deliveryMethodCollect.setName("Click & collect");
        deliveryMethodCollect.setDeliveryTextContent("Available in two hours");
        deliveryMethodCollect.setPrice(0);

        // Collect points
        PickUpPoint collectPoint1 = new PickUpPoint();
        collectPoint1.setId("1");
        collectPoint1.setName("Store 1");
        collectPoint1.setAddress("Address 1");
        collectPoint1.setZipCode("zip");
        collectPoint1.setCity("city");
        collectPoint1.setCountry(Country.fromAlpha2("FR"));

        PickUpPoint collectPoint2 = new PickUpPoint();
        collectPoint2.setId("2");
        collectPoint2.setName("Store 2");
        collectPoint2.setAddress("Address 2");
        collectPoint2.setZipCode("zip");
        collectPoint2.setCity("city");
        collectPoint2.setCountry(Country.fromAlpha2("FR"));

        PickUpPoint collectPoint3 = new PickUpPoint();
        collectPoint3.setId("3");
        collectPoint3.setName("Store 3");
        collectPoint3.setAddress("Address 3");
        collectPoint3.setZipCode("zip");
        collectPoint3.setCity("city");
        collectPoint3.setCountry(Country.fromAlpha2("FR"));

        PickUpPoints pickUpPointsCollect = new PickUpPoints();
        pickUpPointsCollect.add(collectPoint1);
        pickUpPointsCollect.add(collectPoint2);
        pickUpPointsCollect.add(collectPoint3);

        deliveryMethodCollect.setPickUpPoints(pickUpPointsCollect);



        // We add everything to the main object
        DeliveryMethods deliveryMethods = new DeliveryMethods();
        deliveryMethods.add(deliveryMethod);
        deliveryMethods.add(deliveryMethodRelay);
        deliveryMethods.add(deliveryMethodCollect);

        // And we show the json
        System.out.println(deliveryMethods.toJson());
    }
}
``` 

### Get details on completion

When a user logs in or pays with Skeerel, the browser will redirect him 
automatically to your `data-redirect-url` parameter. To retrieve his data, you just have to call the 
following lines

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.getData("ACCESS_TOKEN"));
    }
}
```

For more information about getting user information, you can look
at the classes in the package `com.arcansecurity.skeerel.data`.

### Get details of a payment

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.getPayment(UUID.fromString("PAYMENT_ID")));
    }
}
```

### List payments

Payments are ordered by date DESC. Last payment appears first

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.listPayments()); // last ten payments
        System.out.println(skeerel.listPayments(true)); // last ten live payments
        System.out.println(skeerel.listPayments(15, 20)); // list twenty payments starting from the fifteenth index
        System.out.println(skeerel.listPayments(true, 15, 20)); // list twenty live payments starting from the fifteenth index
    }
}
```

### Refund payment

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.refundPayment(UUID.fromString("PAYMENT_ID"))); // full refund
        System.out.println(skeerel.refundPayment(UUID.fromString("PAYMENT_ID"), 100)); // partial refund (amount is in the currency's smallest unit. Ex 50€ => 5000)
    }
}
```

### Reviewing a payment

When a payment seems suspect, Skeerel hold the money but does not capture it. 

This way you can review the payment manually and only capture the amount if the payment is legit (thus, avoiding dispute). 
If it is fraudulent you can just reject the payment. If the payment is not rejected within seven days, the payment is automatically canceled.

The following code shows how to capture or reject a payment.

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.capturePayment(UUID.fromString(""))); // payment is legit, capture it
        System.out.println(skeerel.rejectPayment(UUID.fromString(""))); // payment is fraudulent, reject it
    }
}
```

### Get details of a website

```java
import com.arcansecurity.skeerel.Skeerel;
import com.arcansecurity.skeerel.exception.SkeerelException;

import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SkeerelException {
        Skeerel skeerel = new Skeerel(UUID.fromString("WEBSITE_ID"), "WEBSITE_SECRET");
        System.out.println(skeerel.getWebsiteDetails());
    }
}
```

# Sample App

TODO
 
# Resources
Check out the [API documentation](http://doc.skeerel.com).  
Access your [customer dashboard](https://admin.skeerel.com).