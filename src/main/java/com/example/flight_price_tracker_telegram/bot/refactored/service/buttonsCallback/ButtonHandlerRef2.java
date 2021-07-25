package com.example.flight_price_tracker_telegram.bot.refactored.service.buttonsCallback;

import com.example.flight_price_tracker_telegram.bot.refactored.Context;
import com.example.flight_price_tracker_telegram.bot.refactored.states.*;
import com.example.flight_price_tracker_telegram.model.localisation.CountryDTO;
import com.example.flight_price_tracker_telegram.model.places.PlacesDTO;
import com.example.flight_price_tracker_telegram.repository.UserSubscription;

import java.util.List;

public class ButtonHandlerRef2 {
   KeyboardMarkups keyboardMarkup;
//   private List<CountryDTO> countryDTOList;
//   private List<PlacesDTO> placesDTOList;
   private List list;
  // private List<UserSubscription> subscriptionList;

   public ButtonHandlerRef2(){};
   public ButtonHandlerRef2(List list){
       this.list=list;
   };


    public KeyboardMarkups getKeyboardMarkup(Context context){
        if (context.getState().equals(new StartState(context))) {
            keyboardMarkup=new StartStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboard();
        } else if(context.getState().equals(new DataFilledState(context))){
            keyboardMarkup=new DataFilledStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboard();
        }else if (context.getState().equals(new CurrencyState(context))) {
            keyboardMarkup=new CurrencyStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboard();
        }else if(context.getState().equals(new DataTransferredState(context))){
            keyboardMarkup=new DataTransferredStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboard();
        }else if(context.getState().equals(new InboundPartialDateState(context))){
            keyboardMarkup=new InboundPartialDateStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboard();
        } else if(context.getState().equals(new CountryButtonState(context))){
            keyboardMarkup=new CountryStateKeyBoard();
            keyboardMarkup.getMessageFromKeyboardCountry(list);
        }else if(context.getState().equals(new OriginPlaceButtons(context)) || context.getState().equals (new DestinationPlaceButtonsState(context))){
            keyboardMarkup=new PlacesStatesKeyBoard();
            keyboardMarkup.getMessageFromKeyboardPlaces(list);
        } else if(context.getState().equals(new OriginPlaceText(context))){
            keyboardMarkup=new MainMenuKeyBoard();
            keyboardMarkup.getMainMenuKeyboard();
        } else if(context.getState().equals(new OriginPlaceText(context))){

        }

        return keyboardMarkup;
    }

}
