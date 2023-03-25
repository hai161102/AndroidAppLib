
package com.haiprj.android_app_lib.my_admobs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.haiprj.android_app_lib.interfaces.PurchaseCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PurchaseManager {
    private static final String TAG = PurchaseManager.class.getName();
    public static final String PRODUCT_SUB_WEEK = "";
    public static final String PRODUCT_SUB_MONTH = "";
    public static final String PRODUCT_LIFETIME = "android.test.purchased";
    private BillingClient billingClient;
    private PurchaseCallback callback;
    @SuppressLint("StaticFieldLeak")
    private static PurchaseManager instance;
    private List<SkuDetails> skuDetailsListIAP = new ArrayList<>();
    private List<SkuDetails> skuDetailsListSUB = new ArrayList<>();
    private boolean isPurchased;
    private final AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            callback.purchaseSuccess();
        }
    };

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public void setCallback(PurchaseCallback callback) {
        this.callback = callback;
    }

    private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {

        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
            // To be implemented in a later section.
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                    && purchases != null) {
                for (int i = 0; i < purchases.size(); i++) {
                    handlePurchase(purchases.get(i));
                }
            } else {
                callback.purchaseFail();
            }
        }
    };

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }
//        ConsumeParams consumeParams =
//                ConsumeParams.newBuilder()
//                        .setPurchaseToken(purchase.getPurchaseToken())
//                        .build();
//
//        ConsumeResponseListener listener = (billingResult, purchaseToken) -> {
//            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                callback.purchaseSucess();
//            }
//        };
//
//        billingClient.consumeAsync(consumeParams, listener);
    }


    public static synchronized PurchaseManager getInstance() {
        if (instance == null) {
            instance = new PurchaseManager();
        }
        return instance;
    }

    private PurchaseManager() {

    }

    public void initBilling(final Context context) {
        billingClient = BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        restore(context, null);
    }

    public void restore(final Context context, PurchaseCallback callback) {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(Collections.singletonList(PRODUCT_LIFETIME)).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(),
                            (billingResult12, skuDetailsList) -> PurchaseManager.this.skuDetailsListIAP = skuDetailsList);

                    params.setSkusList(Arrays.asList(PRODUCT_SUB_WEEK, PRODUCT_SUB_MONTH)).setType(BillingClient.SkuType.SUBS);
                    billingClient.querySkuDetailsAsync(params.build(),
                            (billingResult1, skuDetailsList) -> PurchaseManager.this.skuDetailsListSUB = skuDetailsList);
                    if (callback != null) {
                        if (isPremium(context)) {
                            callback.purchaseSuccess();
                        } else {
                            callback.purchaseFail();
                        }
                    }

                } else {
                    if (callback != null) {
                        callback.purchaseFail();
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                billingClient = null;
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    public void consume(String productId) {
        Purchase purchase = getPurchase(productId);
        if (purchase != null) {
            ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
            billingClient.consumeAsync(consumeParams, (billingResult, s) -> {

            });
        }
    }

    private Purchase getPurchase(String productId) {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        for (Purchase purchase :
                Objects.requireNonNull(purchasesResult.getPurchasesList())) {
            if (purchase.getSkus().get(0).equals(productId)) return purchase;
        }
        return null;
    }

    public boolean isPremium(Context context) {
        if (isPurchased) {
            return true;
        }
        if (billingClient == null) {
            if (context == null) {
                return false;
            }
            initBilling(context);
        }
        return isPurchased() || isSubscribed();
//        return true;
    }

    private boolean isPurchased() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        if (purchasesResult == null || purchasesResult.getPurchasesList() == null) return false;

        for (Purchase purchase :
                purchasesResult.getPurchasesList()) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                return true;
            }
        }
        return false;
    }

    private boolean isSubscribed() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        if (purchasesResult.getPurchasesList() == null) return false;
        for (Purchase purchase :
                purchasesResult.getPurchasesList()) {
            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                return true;
            }
        }
        return false;
    }

    public void purchase(Activity activity, String productId) {
        if (billingClient == null) {
            initBilling(activity);
        }
        SkuDetails skuDetails = getSkuDetail(skuDetailsListIAP, productId);
        if (skuDetails == null) {
            return;
        }
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(activity, billingFlowParams);
    }

    public void subscribe(Activity activity, String productId) {
        if (billingClient == null) {
            initBilling(activity);
        }
        try {
            SkuDetails skuDetails = getSkuDetail(skuDetailsListSUB, productId);
            if (skuDetails == null) return;
            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();
            billingClient.launchBillingFlow(activity, billingFlowParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SkuDetails getSkuDetail(List<SkuDetails> skuDetailsListSUB, String productId) {
        for (SkuDetails skuDetails :
                skuDetailsListSUB) {
            if (skuDetails.getSku().equals(productId)) {
                return skuDetails;
            }
        }
        return null;
    }

    public String getPrice(String productId) {
        if (billingClient == null || !billingClient.isReady()) {
            return "";
        }
        for (SkuDetails skuDetails :
                skuDetailsListSUB) {
            if (skuDetails.getSku().equals(productId)) {
                return skuDetails.getPrice();
            }
        }
        for (SkuDetails skuDetails :
                skuDetailsListIAP) {
            if (skuDetails.getSku().equals(productId)) {
                return skuDetails.getPrice();
            }
        }
        return "";
    }


}
