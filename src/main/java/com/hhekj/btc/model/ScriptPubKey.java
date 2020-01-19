package com.hhekj.btc.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Author: Angel
 * Description:
 * Date: 2020-01-17 15:43
 **/
@Data
public class ScriptPubKey {
    private String asm;
    private String hex;
    private String type;
    private Integer reqSigs;

    private ArrayList<String> addresses;
}
