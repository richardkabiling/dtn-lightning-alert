package com.github.richardkabiling.dtn.lightning.alert;

import static com.github.richardkabiling.dtn.lightning.alert.LightningStrike.FlashType.CLOUD_TO_CLOUD;

import java.util.List;

public class LightningStrikeAlertFixtures {

  public static List<AssetLightningAlert> alerts() {
    return List.of(
        new AssetLightningAlert("023112231123", "Glenda Circle"  , "003"  , CLOUD_TO_CLOUD, 32.8501354, -98.6268688, 1446761098753L, 2707 , "000", 12926, 1446761110182L, 19, 20),
        new AssetLightningAlert("023112302321", "Edie Lodge"     , "5014" , CLOUD_TO_CLOUD, 33.217524 , -97.9474601, 1446761099160L, 2491 , "000", 18197, 1446761111198L, 18, 21),
        new AssetLightningAlert("023112303222", "Stefanie Street", "59276", CLOUD_TO_CLOUD, 33.1563258, -97.6717756, 1446761100302L, -695 , "000", 18305, 1446761112250L, 7 , 1),
        new AssetLightningAlert("023112310312", "Milburn Loop"   , "9896" , CLOUD_TO_CLOUD, 33.8897226, -96.4850085, 1446761109891L, 1589 , "000", 15275, 1446761121392L, 14, 18),
        new AssetLightningAlert("023112131032", "Beer Freeway"   , "31010", CLOUD_TO_CLOUD, 35.2188571, -96.0724577, 1446761112829L, -4663, "000", 14359, 1446761126430L, 20, 2)
    );
  }

  public static List<Asset> assets() {
    return List.of(
        new Asset("Glenda Circle", "023112231123", "003"),
        new Asset("Edie Lodge", "023112302321", "5014"),
        new Asset("Stefanie Street", "023112303222", "59276"),
        new Asset("Milburn Loop", "023112310312", "9896"),
        new Asset("Beer Freeway", "023112131032", "31010")
    );
  }

  public static List<LightningStrike> lightningStrikes() {
    return List.of(
        new LightningStrike(CLOUD_TO_CLOUD, 1446761098753L, 32.8501354, -98.6268688, 2707, "000", 12926, 1446761110182L, 19, 20),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761099160L, 33.217524, -97.9474601, 2491, "000", 18197, 1446761111198L, 18, 21),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761100302L, 33.1563258, -97.6717756, -695, "000", 18305, 1446761112250L, 7, 1),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761109891L, 33.8897226, -96.4850085, 1589, "000", 15275, 1446761121392L, 14, 18),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761112829L, 35.2188571, -96.0724577, -4663, "000", 14359, 1446761126430L, 20, 2)
    );
  }

  public static List<LightningStrike> lightningStrikesWithoutAsset() {
    return List.of(
        new LightningStrike(CLOUD_TO_CLOUD, 1446761098753L, 8.7020156, -12.2736188, 2707, "000", 12926, 1446761110182L, 19, 20),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761099160L, 10.5799716, -14.0589797, 2491, "000", 18197, 1446761111198L, 18, 21),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761100302L, 8.6972308, -12.2895479, -695, "000", 18305, 1446761112250L, 7, 1),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761109891L, 8.6764402, -12.2806221, 1589, "000", 15275, 1446761121392L, 14, 18),
        new LightningStrike(CLOUD_TO_CLOUD, 1446761112829L, 8.7053152, -12.2771736, -4663, "000", 14359, 1446761126430L, 20, 2)
    );
  }

  private LightningStrikeAlertFixtures() {

  }
}
