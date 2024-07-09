package com.example.springj310.service;
import org.springframework.stereotype.Component;

@Component
public class ControlForClientAndAddress {
    public String controlForClientAndAddress(String name, String mac, String ip,String address, String model) {
        //if(name!=null|| !name.matches("[а-яёА-ЯЁ, .-]{1,100}")){name="ф";}
       // if(mac==null){mac="1A-1B-2C-4E-8H-9";}
       ///if(ip==null){ip="255.255.255.255";}
       // if(address==null){address="ф";}
       // if(model==null){model="ф";}
            if (name!=null||!name.matches("[а-яёА-ЯЁ, .-]{1,100}")) {
                    return "Наименование клиента некорректно";

                }else if (!mac.matches("([\\dA-Za-z]{1,2}-){5,5}([\\dA-Za-z]{1,2})")){
               return "MAC некорректный";
            }else if (!ip.matches("^([0-1][\\d][\\d].|2[0-4][\\d].|25[0-5].){3,3}([0-1][\\d][\\d]|2[0-4][\\d]|25[0-5])")){
           return "ip некорректный";
           }else if (address.isEmpty()){ return "Не заполнен адрес";
    }else if (model.isEmpty()){ return "Не заполнена модель";}
        return "OK";
    }
    }
