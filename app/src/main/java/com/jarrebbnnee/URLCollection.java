package com.jarrebbnnee;

/**
 * Created by vi6 on 16-Mar-17.
 */

public class URLCollection {

    public  final String mainUrl = "http://pr.veba.co/~shubantech/jarrebbnnee/jarrebbnnee_api/";
    public  String countryList = mainUrl + "index.php?action=countryList&lang="+LanguageGetSet.getLang();
    public  String RegisterUrl = mainUrl+"index.php?action=userRagister&lang="+LanguageGetSet.getLang();
    public  String LoginUrl = mainUrl+"index.php?action=userLogin&lang="+LanguageGetSet.getLang();;
    public  String categoryListing = mainUrl+"index.php?action=getCategoryList&lang="+LanguageGetSet.getLang();
    public  String productListing = mainUrl+"index.php?action=getProductList&lang="+LanguageGetSet.getLang();
    public  String productDetails = mainUrl+"index.php?action=getProductDetail&lang="+LanguageGetSet.getLang();
    public  String addToCart = mainUrl+"index.php?action=addToCart&lang="+LanguageGetSet.getLang();
    public  String removeFromCart = mainUrl+"index.php?action=deleteFromCart&lang="+LanguageGetSet.getLang();
    public  String updateQuantity = mainUrl+"index.php?action=updateQuntityCartItem&lang="+LanguageGetSet.getLang();
    public  String cartListing = mainUrl+"index.php?action=getMyCart&lang="+LanguageGetSet.getLang();
    public  String addComment = mainUrl+"index.php?action=addComment&lang="+LanguageGetSet.getLang();
    public  String addWishlist = mainUrl+"index.php?action=addWishList&lang="+LanguageGetSet.getLang();
    public  String deleteFromWishlist = mainUrl+"index.php?action=deleteWishList&lang="+LanguageGetSet.getLang();
    public  String getWishlist = mainUrl+"index.php?action=getWishList&lang="+LanguageGetSet.getLang();
    public  String homeSlider = mainUrl+"index.php?action=getHomeSlider&lang="+LanguageGetSet.getLang();
    public  String SocialLoginUrl = mainUrl+"index.php?action=socialLogin&lang="+LanguageGetSet.getLang();
    public String filter = mainUrl + "index.php?action=getFilterResult&lang="+LanguageGetSet.getLang();
}
