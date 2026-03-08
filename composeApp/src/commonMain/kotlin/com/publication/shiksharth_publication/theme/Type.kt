package com.publication.shared.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import shiksharth_kmp.composeapp.generated.resources.Res
import shiksharth_kmp.composeapp.generated.resources.roboto_bold
import shiksharth_kmp.composeapp.generated.resources.roboto_medium
import shiksharth_kmp.composeapp.generated.resources.roboto_regular
import shiksharth_kmp.composeapp.generated.resources.roboto_semibold
import kotlin.invoke

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Composable
fun robotoFontFamily(): FontFamily {
    return FontFamily(
        Font(resource = Res.font.roboto_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.roboto_medium, weight = FontWeight.Medium),
        Font(resource = Res.font.roboto_semibold, weight = FontWeight.SemiBold),
        Font(resource = Res.font.roboto_bold, weight = FontWeight.Bold)
    )
}

@Composable
fun robotoFont(): FontFamily {
    return robotoFontFamily()
}


@Composable
fun appTypography(): Typography {
    val roboto = robotoFontFamily()
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = roboto,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
}