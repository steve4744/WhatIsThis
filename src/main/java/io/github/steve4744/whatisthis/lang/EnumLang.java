/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package io.github.steve4744.whatisthis.lang;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.steve4744.whatisthis.WhatIsThis;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

import org.bukkit.Bukkit;

/**
 * Created by Meow J on 6/20/2015.
 * <p>
 * Unlocalized Name to Localized Name
 *
 * @author Meow J
 */
public enum EnumLang {

	AF_ZA("af_za", new HashMap<String, String>()),
	AR_SA("ar_sa", new HashMap<String, String>()),
	AST_ES("ast_es", new HashMap<String, String>()),
	AZ_AZ("az_az", new HashMap<String, String>()),
	BAR("bar", new HashMap<String, String>()),
	BA_RU("ba_ru", new HashMap<String, String>()),
	BE_BY("be_by", new HashMap<String, String>()),
	BE_LATN("be_latn", new HashMap<String, String>()),
	BG_BG("bg_bg", new HashMap<String, String>()),
	BRB("brb", new HashMap<String, String>()),
	BR_FR("br_fr", new HashMap<String, String>()),
	BS_BA("bs_ba", new HashMap<String, String>()),
	CA_ES("ca_es", new HashMap<String, String>()),
	CS_CZ("cs_cz", new HashMap<String, String>()),
	CY_GB("cy_gb", new HashMap<String, String>()),
	DA_DK("da_dk", new HashMap<String, String>()),
	DE_AT("de_at", new HashMap<String, String>()),
	DE_CH("de_ch", new HashMap<String, String>()),
	DE_DE("de_de", new HashMap<String, String>()),
	EL_GR("el_gr", new HashMap<String, String>()),
	ENP("enp", new HashMap<String, String>()),
	ENWS("enws", new HashMap<String, String>()),
	EN_AU("en_au", new HashMap<String, String>()),
	EN_CA("en_ca", new HashMap<String, String>()),
	EN_GB("en_gb", new HashMap<String, String>()),
	EN_NZ("en_nz", new HashMap<String, String>()),
	EN_PT("en_pt", new HashMap<String, String>()),
	EN_UD("en_ud", new HashMap<String, String>()),
	EN_US("en_us", new HashMap<String, String>()),
	EO_UY("eo_uy", new HashMap<String, String>()),
	ESAN("esan", new HashMap<String, String>()),
	ES_AR("es_ar", new HashMap<String, String>()),
	ES_CL("es_cl", new HashMap<String, String>()),
	ES_EC("es_ec", new HashMap<String, String>()),
	ES_ES("es_es", new HashMap<String, String>()),
	ES_MX("es_mx", new HashMap<String, String>()),
	ES_UY("es_uy", new HashMap<String, String>()),
	ES_VE("es_ve", new HashMap<String, String>()),
	ET_EE("et_ee", new HashMap<String, String>()),
	EU_ES("eu_es", new HashMap<String, String>()),
	FA_IR("fa_ir", new HashMap<String, String>()),
	FIL_PH("fil_ph", new HashMap<String, String>()),
	FI_FI("fi_fi", new HashMap<String, String>()),
	FO_FO("fo_fo", new HashMap<String, String>()),
	FRA_DE("fra_de", new HashMap<String, String>()),
	FR_CA("fr_ca", new HashMap<String, String>()),
	FR_FR("fr_fr", new HashMap<String, String>()),
	FUR_IT("fur_it", new HashMap<String, String>()),
	FY_NL("fy_nl", new HashMap<String, String>()),
	GA_IE("ga_ie", new HashMap<String, String>()),
	GD_GB("gd_gb", new HashMap<String, String>()),
	GL_ES("gl_es", new HashMap<String, String>()),
	HAW_US("haw_us", new HashMap<String, String>()),
	HE_IL("he_il", new HashMap<String, String>()),
	HI_IN("hi_in", new HashMap<String, String>()),
	HN_NO("hn_no", new HashMap<String, String>()),
	HR_HR("hr_hr", new HashMap<String, String>()),
	HU_HU("hu_hu", new HashMap<String, String>()),
	HY_AM("hy_am", new HashMap<String, String>()),
	ID_ID("id_id", new HashMap<String, String>()),
	IG_NG("ig_ng", new HashMap<String, String>()),
	IO_EN("io_en", new HashMap<String, String>()),
	ISV("isv", new HashMap<String, String>()),
	IS_IS("is_is", new HashMap<String, String>()),
	IT_IT("it_it", new HashMap<String, String>()),
	JA_JP("ja_jp", new HashMap<String, String>()),
	JBO_EN("jbo_en", new HashMap<String, String>()),
	KA_GE("ka_ge", new HashMap<String, String>()),
	KK_KZ("kk_kz", new HashMap<String, String>()),
	KN_IN("kn_in", new HashMap<String, String>()),
	KO_KR("ko_kr", new HashMap<String, String>()),
	KSH("ksh", new HashMap<String, String>()),
	KW_GB("kw_gb", new HashMap<String, String>()),
	KY_KG("ky_kg", new HashMap<String, String>()),
	LA_LA("la_la", new HashMap<String, String>()),
	LB_LU("lb_lu", new HashMap<String, String>()),
	LI_LI("li_li", new HashMap<String, String>()),
	LMO("lmo", new HashMap<String, String>()),
	LOL_US("lol_us", new HashMap<String, String>()),
	LO_LA("lo_la", new HashMap<String, String>()),
	LT_LT("lt_lt", new HashMap<String, String>()),
	LV_LV("lv_lv", new HashMap<String, String>()),
	LZH("lzh", new HashMap<String, String>()),
	MK_MK("mk_mk", new HashMap<String, String>()),
	MN_MN("mn_mn", new HashMap<String, String>()),
	MS_MY("ms_my", new HashMap<String, String>()),
	MT_MT("mt_mt", new HashMap<String, String>()),
	NAH("nah", new HashMap<String, String>()),
	NDS_DE("nds_de", new HashMap<String, String>()),
	NL_BE("nl_be", new HashMap<String, String>()),
	NL_NL("nl_nl", new HashMap<String, String>()),
	NN_NO("nn_no", new HashMap<String, String>()),
	NO_NO("no_no", new HashMap<String, String>()),
	OC_FR("oc_fr", new HashMap<String, String>()),
	OVD("ovd", new HashMap<String, String>()),
	PLS("pls", new HashMap<String, String>()),
	PL_PL("pl_pl", new HashMap<String, String>()),
	PT_BR("pt_br", new HashMap<String, String>()),
	PT_PT("pt_pt", new HashMap<String, String>()),
	QYA_AA("qya_aa", new HashMap<String, String>()),
	RO_RO("ro_ro", new HashMap<String, String>()),
	RPR("rpr", new HashMap<String, String>()),
	RU_RU("ru_ru", new HashMap<String, String>()),
	RY_UA("ry_ua", new HashMap<String, String>()),
	SAH_SAH("sah_sah", new HashMap<String, String>()),
	SE_NO("se_no", new HashMap<String, String>()),
	SK_SK("sk_sk", new HashMap<String, String>()),
	SL_SI("sl_si", new HashMap<String, String>()),
	SO_SO("so_so", new HashMap<String, String>()),
	SQ_AL("sq_al", new HashMap<String, String>()),
	SR_CS("sr_cs", new HashMap<String, String>()),
	SR_SP("sr_sp", new HashMap<String, String>()),
	SV_SE("sv_se", new HashMap<String, String>()),
	SXU("sxu", new HashMap<String, String>()),
	SZL("szl", new HashMap<String, String>()),
	TA_IN("ta_in", new HashMap<String, String>()),
	TH_TH("th_th", new HashMap<String, String>()),
	TLH_AA("tlh_aa", new HashMap<String, String>()),
	TL_PH("tl_ph", new HashMap<String, String>()),
	TOK("tok", new HashMap<String, String>()),
	TR_TR("tr_tr", new HashMap<String, String>()),
	TT_RU("tt_ru", new HashMap<String, String>()),
	TZO_MX("tzo_mx", new HashMap<String, String>()),
	UK_UA("uk_ua", new HashMap<String, String>()),
	VAL_ES("val_es", new HashMap<String, String>()),
	VEC_IT("vec_it", new HashMap<String, String>()),
	VI_VN("vi_vn", new HashMap<String, String>()),
	VP_VL("vp_vl", new HashMap<String, String>()),
	YI_DE("yi_de", new HashMap<String, String>()),
	YO_NG("yo_ng", new HashMap<String, String>()),
	ZH_CN("zh_cn", new HashMap<String, String>()),
	ZH_HK("zh_hk", new HashMap<String, String>()),
	ZH_TW("zh_tw", new HashMap<String, String>()),
	ZLM_ARAB("zlm_arab", new HashMap<String, String>());


    private static final Map<String, EnumLang> lookup = new HashMap<>();

    static {
        for (EnumLang lang : EnumSet.allOf(EnumLang.class))
            lookup.put(lang.getLocale(), lang);
    }

    private final String locale;
    private final Map<String, String> map;

    /**
     * Create an index of lang file.
     */
    EnumLang(String locale, Map<String, String> map) {
        this.locale = locale;
        this.map = map;
    }

    /**
     * @param locale The locale of the language
     * @return The index of a lang file based on locale.
     */
    public static EnumLang get(String locale) {
        EnumLang result = lookup.get(locale.toLowerCase(Locale.ENGLISH));
        return result == null ? EN_US : result;
    }

    /**
     * Initialise this class, load all the languages to the corresponding HashMap.
     */
    public static void init() {
    	final String PREFIX = "[WhatIsThis] ";
        for (EnumLang enumLang : EnumLang.values()) {

            if (!WhatIsThis.getPlugin().getSettings().getLangs().contains("all") && !WhatIsThis.getPlugin().getSettings().getLangs().contains(enumLang.getLocale())) {
                continue;
            }
            try {
                readFile(enumLang, new BufferedReader(new InputStreamReader(EnumLang.class.getResourceAsStream("/lang/" + enumLang.locale + ".json"), Charset.forName("UTF-8"))));
                Bukkit.getLogger().info(PREFIX + enumLang.getLocale() + " has been loaded.");
            } catch (Exception e) {
            	Bukkit.getLogger().info(PREFIX + "Fail to load language file " + enumLang.getLocale());
                e.printStackTrace();
            }
        }
        File customizedLangDir = new File(WhatIsThis.getPlugin().getDataFolder(), "lang");
        if (!customizedLangDir.exists()) {
            customizedLangDir.mkdirs();
        }

        for (File file : Objects.requireNonNull(customizedLangDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json")))) {
            EnumLang enumLang = get(file.getName()); // Returns EN_US when language not found.
            if (enumLang.getLocale().equals(EN_US.locale) && !file.getName().contains(EN_US.locale)) {
                Bukkit.getLogger().info(PREFIX + "Failed to load customized language file " + file.getName()); // Language not present
            } else {
                try (FileInputStream fs = new FileInputStream(file)) {
                    readFile(enumLang, new BufferedReader(new InputStreamReader(fs, Charset.forName("UTF-8"))));
                    Bukkit.getLogger().info(PREFIX + "Failed to load customized language file " + file.getName()); // Error loading language files
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Clean all the HashMap
     */
    public static void clean() {
        for (EnumLang enumLang : EnumLang.values()) {
            enumLang.getMap().clear();
        }
    }

    private static void readFile(EnumLang enumLang, BufferedReader reader) throws IOException {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> map = gson.fromJson(reader, type);
            enumLang.map.putAll(map);
        } finally {
            reader.close();
        }
    }

    /**
     * @return The locale of the language
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @return The HashMap of the language.
     */
    public Map<String, String> getMap() {
        return map;
    }
}

