package com.mobile.linkwallet.Api;




import com.mobile.linkwallet.models.BackupPhrase_Response;
import com.mobile.linkwallet.models.CancelResponse;
import com.mobile.linkwallet.models.CoinListResponse;
import com.mobile.linkwallet.models.CreateResponse;
import com.mobile.linkwallet.models.DefaultResponse;
import com.mobile.linkwallet.models.HistoryResponse;
import com.mobile.linkwallet.models.ListResponse;
import com.mobile.linkwallet.models.LoanResponse;
import com.mobile.linkwallet.models.LoginResponse;
import com.mobile.linkwallet.models.MessageResponse;
import com.mobile.linkwallet.models.RecoverResponse;
import com.mobile.linkwallet.models.Recovercode_Response;
import com.mobile.linkwallet.models.ResetResponse;
import com.mobile.linkwallet.models.SetNewResponse;
import com.mobile.linkwallet.models.StakeResp;
import com.mobile.linkwallet.models.StakeResponse;
import com.mobile.linkwallet.models.StakesResponse;
import com.mobile.linkwallet.models.SwapResp;
import com.mobile.linkwallet.models.SwaplistResp;
import com.mobile.linkwallet.models.SwaplistResponse;
import com.mobile.linkwallet.models.TradeResponse;
import com.mobile.linkwallet.models.Trans_Response;
import com.mobile.linkwallet.models.ValidateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> createUser(
          @Field("cmd") String cmd,
          @Field("firstname") String first,
          @Field("lastname") String last,
          @Field("email") String emails,
          @Field("country") String country,
          @Field("language") String language,
          @Field("password") String password,
          @Field("password2") String password2
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<LoginResponse> userLogin(
          @Field("user") String email,
          @Field("password") String password,
          @Field("language") String language,
          @Field("cmd") String cmd
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<RecoverResponse> recover(
          @Field("user") String remail,
          @Field("language") String language,
          @Field("cmd") String cmd
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<Recovercode_Response> recover_code(
          @Field("email") String p_email,
          @Field("code") String Code,
          @Field("language") String language,
          @Field("cmd") String Cmd
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<ResetResponse> reset(
          @Field("email") String Resetemail,
          @Field("code") String Rcode,
          @Field("password") String Password1,
          @Field("password2") String Password2,
          @Field("language") String language,
          @Field("cmd") String Rcmd
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<BackupPhrase_Response> backup(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<ValidateResponse> validate(
          @Field("phrase") String Phrase,
          @Field("language") String language,
          @Field("cmd") String Cmd
  );


  @FormUrlEncoded
  @POST("api.json")
  Call<SetNewResponse> setnew(
          @Field("email") String Setemail,
          @Field("code") String Recovery_code,
          @Field("password") String Password5,
          @Field("password2") String Password6,
          @Field("wallet_password") String Password7,
          @Field("wallet_password2") String Password8,
          @Field("cmd") String Rcmd
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<CreateResponse> create2fa(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<CancelResponse> deactivate_2fa(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("code") String Google_code,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<LoginResponse> verify_2fa(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("code") String Google_code,
          @Field("language") String language,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<CoinListResponse> getcoinlist(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("cmd") String cmd

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<SetNewResponse> create_wallet(
          @Field("user_id") int ID,
          @Field("email") String Email,
          @Field("coin_id") int Coin,
          @Field("wallet_name") String Wallet_Name,
          @Field("wallet_password") String Wallet_Password,
          @Field("wallet_password2") String Wallet_Password2,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<ListResponse> listwallet(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("limit") int limit,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken

  );
  @FormUrlEncoded
  @POST("api.json")
  Call<LoginResponse> activate_2fa(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("code") String Google_code,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<CancelResponse> verify_email(
          @Field("id") int id,
          @Field("email") String Vemail,
          @Field("code") String Code,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );
  @FormUrlEncoded
  @POST("api.json")
  Call<CancelResponse> resend_code(
          @Field("user") String vemail,
          @Field("id") int Id,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> all_balance(
          @Field("id") int Id,
          @Field("email") String email,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> edit_password(
          @Field("email") String Email,
          @Field("id") int Id,
          @Field("opassword") String Ppwd,
          @Field("password") String Npwd,
          @Field("password2") String Cnpwd,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<ListResponse> showall(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<MessageResponse> noty(
          @Field("id") int id,
          @Field("email") String Email,
          @Field("type") String Type,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<CancelResponse> view_noty(
          @Field("id") int id,
          @Field("email") String email,
          @Field("notify_id") String notify_id,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<Recovercode_Response> review(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("password") String password,
          @Field("wallet_id") String wallet_id,
          @Field("amount") String amount,
          @Field("wallet_address") String wallet_address,
          @Field("currency_type") String currency_type,
          @Field("wallet_type") String wallet_type,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<HistoryResponse>trans(
          @Field("id") int id,
          @Field("email") String email,
          @Field("wallet_id") String wallet_id,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<ResetResponse> receive(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("wallet_id") String wallet_id,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<Trans_Response> view_trans(
          @Field("id") int id,
          @Field("email") String email,
          @Field("transaction_id") String transaction_id,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> edit_lang(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("language_code") String language,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> edit_curr(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("currency_code") String currency,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> otc_form(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("asset") String asset,
          @Field("asset_volume") String volume,
          @Field("quote_price") String price,
          @Field("name") String fname,
          @Field("date_of_birth") String date,
          @Field("nationality") String nationality,
          @Field("id_type") String idtype,
          @Field("id_number") String id_no,
          @Field("residence") String address,
          @Field("country") String country,
          @Field("mobile_number") String mn,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<DefaultResponse> loan_form(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("firstname") String fname,
          @Field("lastname") String lname,
          @Field("phone") String mn,
          @Field("email_address") String email2,
          @Field("company") String company,
          @Field("website") String website,
          @Field("business") String tob,
          @Field("service") String service,
          @Field("amount") String loan,
          @Field("portfolio_size") String psize,
          @Field("country") String country,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<LoanResponse> loan_list(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<TradeResponse> otc_list(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );


  @FormUrlEncoded
  @POST("api.json")
  Call<SwaplistResponse> swap_list(
          @Field("id") int id,
          @Field("email") String email,
          @Field("coin_code_from") String froms,
          @Field("coin_code_to") String tos,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<SwapResp> swap(
          @Field("id") int id,
          @Field("email") String email,
          @Field("coin_code_from") String froms,
          @Field("coin_code_to") String tos,
          @Field("wallet_from_id") String from_id,
          @Field("wallet_to_id") String to_id,
          @Field("amount") String amount,
          @Field("cmd") String Cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<SwaplistResp> all_swap(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<StakesResponse>stakes(
          @Field("user_id") int id,
          @Field("email") String email,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken

  );

  @FormUrlEncoded
  @POST("api.json")
  Call<StakeResp> stake_list(
          @Field("id") int id,
          @Field("email") String email,
          @Field("stake_coin") String coins,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken
  );

  @FormUrlEncoded
  @POST("api.json")
  Call<StakeResponse> stake(
          @Field("id") int id,
          @Field("email") String email,
          @Field("stake_coin") String coin,
          @Field("stake_wallet") String wallet,
          @Field("stake_plan") String plan,
          @Field("stake_amount") String amount,
          @Field("cmd") String cmd,
          @Header("Authorization") String authToken
  );

    @FormUrlEncoded
    @POST("api.json")
    Call<CoinListResponse> listcoinswap(
            @Field("id") int id,
            @Field("email") String Email,
            @Field("cmd") String cmd

    );
}




