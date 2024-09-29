package com.am.recipe.presentation.model

import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.PathParser

enum class BgIcon(val pathNodes: List<PathNode>) {
    AREA(IconPath.area),
    INGREDIENT(IconPath.ingredient),
    CATEGORY(IconPath.category),
    PIZZA(IconPath.pizza),
    ORANGE(IconPath.orange),
    EGG(IconPath.egg),
    RECIPE(IconPath.recipe),
    COOKIE(IconPath.cookie)
}

private object IconPath {

    val category = PathParser().parsePathString(
        "M297,379L446,136Q452,126 461,121.5Q470,117 480,117Q490,117 499,121.5Q508,126 514,136L663,379Q669,389 669," +
                "400Q669,411 664,420Q659,429 650,434.5Q641,440 629,440L331,440Q319,440 310,434.5Q301,429 296,420Q291," +
                "411 291,400Q291,389 297,379ZM700,880Q625,880 572.5,827.5Q520,775 520,700Q520,625 572.5,572.5Q625,520 700," +
                "520Q775,520 827.5,572.5Q880,625 880,700Q880,775 827.5,827.5Q775,880 700,880ZM120,820L120,580Q120,563 131.5," +
                "551.5Q143,540 160,540L400,540Q417,540 428.5,551.5Q440,563 440,580L440,820Q440,837 428.5,848.5Q417,860 400," +
                "860L160,860Q143,860 131.5,848.5Q120,837 120,820Z"
    ).toNodes()

    val area = PathParser().parsePathString(
        "M480,880Q397,880 324,848.5Q251,817 197,763Q143,709 111.5,636Q80,563 80,480Q80,397 111.5," +
                "324Q143,251 197,197Q251,143 324,111.5Q397,80 480,80Q563,80 636,111.5Q709,143 763," +
                "197Q817,251 848.5,324Q880,397 880,480Q880,563 848.5,636Q817,709 763,763Q709,817 636," +
                "848.5Q563,880 480,880ZM440,798L440,720Q407,720 383.5,696.5Q360,673 360,640L360,600L168," +
                "408Q165,426 162.5,444Q160,462 160,480Q160,601 239.5,692Q319,783 440,798ZM716,696Q736,674 752," +
                "648.5Q768,623 778.5,595.5Q789,568 794.5,539Q800,510 800,480Q800,382 745.5,301Q691,220 600,184L600," +
                "200Q600,233 576.5,256.5Q553,280 520,280L440,280L440,360Q440,377 428.5,388.5Q417,400 400,400L320,400L320," +
                "480L560,480Q577,480 588.5,491.5Q600,503 600,520L600,640L640,640Q666,640 687,655.5Q708,671 716,696Z"
    ).toNodes()

    val ingredient = PathParser().parsePathString(
        "M640,880Q540,880 470,810Q400,740 400,640Q400,540 470,470Q540,400 640,400Q740,400 810,470Q880," +
                "540 880,640Q880,740 810,810Q740,880 640,880ZM640,800Q706,800 753,753Q800,706 800,640Q800," +
                "574 753,527Q706,480 640,480Q574,480 527,527Q480,574 480,640Q480,706 527,753Q574,800 640," +
                "800ZM160,800Q127,800 103.5,776.5Q80,753 80,720L80,416Q80,408 81.5,400Q83,392 86,384L166," +
                "200L160,200Q143,200 131.5,188.5Q120,177 120,160L120,120Q120,103 131.5,91.5Q143,80 160,80L440," +
                "80Q457,80 468.5,91.5Q480,103 480,120L480,160Q480,177 468.5,188.5Q457,200 440,200L434,200L500," +
                "352Q481,362 464,373Q447,384 432,398L348,200L252,200L160,416L160,720Q160,720 160,720Q160,720 160," +
                "720L330,720Q335,741 343.5,761.5Q352,782 364,800L160,800ZM640,360Q598,360 569,331Q540,302 540," +
                "260Q540,218 569,189Q598,160 640,160L640,360Q640,318 669,289Q698,260 740,260Q782,260 811,289Q840," +
                "318 840,360L640,360Z"
    ).toNodes()

    val pizza = PathParser().parsePathString(
        "M480,120Q568,120 659,150Q750,180 820,234Q836,246 844,263Q852,280 852,298Q852,309 848.5,320.5Q845," +
                "332 838,343L547,780Q535,798 517,807Q499,816 480,816Q461,816 443,807Q425,798 413,780L122," +
                "343Q115,332 112,321Q109,310 109,299Q109,281 117,264Q125,247 141,235Q211,182 301.5,151Q392," +
                "120 480,120ZM380,400Q405,400 422.5,382.5Q440,365 440,340Q440,315 422.5,297.5Q405,280 380," +
                "280Q355,280 337.5,297.5Q320,315 320,340Q320,365 337.5,382.5Q355,400 380,400ZM480,600Q505," +
                "600 522.5,582.5Q540,565 540,540Q540,515 522.5,497.5Q505,480 480,480Q455,480 437.5,497.5Q420," +
                "515 420,540Q420,565 437.5,582.5Q455,600 480,600Z"
    ).toNodes()

    val orange = PathParser().parsePathString(
        "M480,840Q363,840 281.5,758.5Q200,677 200,560Q200,466 255.5,391.5Q311,317 401,291Q368,283 343.5,262.5Q319,242 304," +
                "213Q289,184 283,150Q277,116 281,81Q322,76 359,86Q396,96 426,118Q456,140 475.5,171Q495,202 499,241Q512," +
                "210 533.5,179.5Q555,149 600,104L656,160Q613,203 592.5,233Q572,263 564,293Q652,321 706,394.5Q760,468 760," +
                "560Q760,677 678.5,758.5Q597,840 480,840Z"
    ).toNodes()

    val egg = PathParser().parsePathString(
        "M480,840Q363,840 281.5,758.5Q200,677 200,560Q200,483 225.5,405Q251,327 291.5,263.5Q332,200 382," +
                "160Q432,120 480,120Q529,120 578.5,160Q628,200 668.5,263.5Q709,327 734.5,405Q760,483 760," +
                "560Q760,677 678.5,758.5Q597,840 480,840ZM520,720Q537,720 548.5,708.5Q560,697 560,680Q560," +
                "663 548.5,651.5Q537,640 520,640Q470,640 435,605Q400,570 400,520Q400,503 388.5,491.5Q377," +
                "480 360,480Q343,480 331.5,491.5Q320,503 320,520Q320,603 378.5,661.5Q437,720 520,720Z"
    ).toNodes()

    val cookie = PathParser().parsePathString(
        "M480,880q-83,0 -156,-31.5T197,763q-54,-54 -85.5,-127T80,480q0,-81 33.5,-157.5t93," +
                "-134.5Q266,130 348,100t180,-18q15,2 23,12.5t9,28.5q2,64 47.5,109.5T716,280q21,1 32," +
                "12t12,34q2,42 25.5,69t65.5,41q14,5 21.5,14.5T880,474q2,83 -29,157t-85,129.5Q712," +
                "816 638,848T480,880ZM420,400q25,0 42.5,-17.5T480,340q0,-25 -17.5,-42.5T420,280q-25," +
                "0 -42.5,17.5T360,340q0,25 17.5,42.5T420,400ZM340,600q25,0 42.5,-17.5T400,540q0," +
                "-25 -17.5,-42.5T340,480q-25,0 -42.5,17.5T280,540q0,25 17.5,42.5T340,600ZM600,640q17," +
                "0 28.5,-11.5T640,600q0,-17 -11.5,-28.5T600,560q-17,0 -28.5,11.5T560,600q0,17 11.5,28.5T600,640Z"
    ).toNodes()

    val recipe = PathParser().parsePathString(
        "M906.48,337.84c0,-117.28 -95.96,-213.24 -213.24,-213.24 -17.77,0 -31.99,3.55 -49.76," +
                "7.11C604.39,81.95 547.53,53.52 480,53.52c-67.53,0 -124.39,28.43 -163.48,78.19C298.75," +
                "128.15 284.53,124.6 266.76,124.6 149.48,124.6 53.52,220.56 53.52,337.84c0,92.4 60.42," +
                "170.59 142.16,202.58L195.68,870.94c0,21.32 14.22,35.54 35.54,35.54l497.56,0c21.32," +
                "0 35.54,-14.22 35.54,-35.54l0,-330.52c81.74,-31.99 142.16,-110.17 142.16,-202.58zM693.24,835.4L266.76," +
                "835.4l0,-71.08l426.48,0l0,71.08zM693.24,480l0,248.78L266.76,728.78l0,-248.78c-78.19,0 -142.16," +
                "-63.97 -142.16,-142.16s63.97,-142.16 142.16,-142.16l10.66,0c24.88,3.55 49.76,10.66 67.53," +
                "24.88 10.66,-24.88 21.32,-42.65 42.65,-60.42 24.88,-21.32 56.86,-35.54 92.4,-35.54s67.53," +
                "14.22 92.4,35.54c17.77,14.22 31.99,35.54 39.09,60.42 21.32,-14.22 46.2,-21.32 71.08," +
                "-24.88l10.66,0c78.19,0 142.16,63.97 142.16,142.16s-63.97,142.16 -142.16,142.16z"
    ).toNodes()

}