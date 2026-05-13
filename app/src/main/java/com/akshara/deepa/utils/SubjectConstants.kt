package com.akshara.deepa.utils

import com.akshara.deepa.R
import com.akshara.deepa.data.models.Subject

object SubjectConstants {

    fun getSubjects(mediumCode: String): List<Subject> =
        if (mediumCode == "en") englishSubjects() else kannadaSubjects()

    private fun englishSubjects() = listOf(
        Subject("en_science","en","Science","Science","🔬",R.color.science_color,R.drawable.bg_subject_science,"en/science"),
        Subject("en_maths","en","Mathematics","Mathematics","📐",R.color.math_color,R.drawable.bg_subject_math,"en/maths"),
        Subject("en_social","en","Social Science","Social Science","🌍",R.color.social_color,R.drawable.bg_subject_social,"en/social"),
        Subject("en_english1","en","English (1st Lang)","English (1st Lang)","A",R.color.english_color,R.drawable.bg_subject_english,"en/english1"),
        Subject("en_english2","en","English (2nd Lang)","English (2nd Lang)","E",R.color.info,R.drawable.bg_subject_science,"en/english2"),
        Subject("en_kannada1","en","Kannada (1st Lang)","Kannada (1st Lang)","ಕ",R.color.kannada_color,R.drawable.bg_subject_kannada,"en/kannada1"),
        Subject("en_kannada2","en","Kannada (2nd Lang)","Kannada (2nd Lang)","ಕ",R.color.kannada_color,R.drawable.bg_subject_kannada,"en/kannada2"),
        Subject("en_hindi","en","Hindi","Hindi","ह",R.color.warning,R.drawable.bg_subject_social,"en/hindi"),
        Subject("en_pe","en","Physical Education","Physical Education","🏃",R.color.success,R.drawable.bg_subject_english,"en/pe")
    )

    private fun kannadaSubjects() = listOf(
        Subject("kn_science","kn","Science","ವಿಜ್ಞಾನ","🔬",R.color.science_color,R.drawable.bg_subject_science,"kn/science"),
        Subject("kn_maths","kn","Mathematics","ಗಣಿತ","📐",R.color.math_color,R.drawable.bg_subject_math,"kn/maths"),
        Subject("kn_social","kn","Social Science","ಸಮಾಜ ವಿಜ್ಞಾನ","🌍",R.color.social_color,R.drawable.bg_subject_social,"kn/social"),
        Subject("kn_english2","kn","English (2nd Lang)","ಇಂಗ್ಲಿಷ್ (2ನೇ ಭಾಷೆ)","E",R.color.english_color,R.drawable.bg_subject_english,"kn/english2"),
        Subject("kn_kannada1","kn","Kannada (1st Lang)","ಕನ್ನಡ (1ನೇ ಭಾಷೆ)","ಕ",R.color.kannada_color,R.drawable.bg_subject_kannada,"kn/kannada1"),
        Subject("kn_hindi","kn","Hindi","ಹಿಂದಿ","ह",R.color.warning,R.drawable.bg_subject_social,"kn/hindi"),
        Subject("kn_pe","kn","Physical Education","ದೈಹಿಕ ಶಿಕ್ಷಣ","🏃",R.color.success,R.drawable.bg_subject_english,"kn/pe")
    )

    val QUOTES_EN = listOf(
        "Education is the most powerful weapon to change the world. — Mandela",
        "No one can take away what you learn. Keep going!",
        "Success = small efforts repeated every day.",
        "One chapter at a time — one victory at a time!",
        "Hard work beats talent when talent doesn't work hard.",
        "Your future is created by what you do TODAY.",
        "Believe you can and you're halfway there.",
        "Don't watch the clock — keep going!"
    )
    val QUOTES_KN = listOf(
        "ವಿದ್ಯೆ ಇಲ್ಲದ ಮನುಷ್ಯ ಪಶುವಿನಂತೆ — ಕನ್ನಡ ಗಾದೆ",
        "ಕಲಿಯುವ ಮನಸ್ಸಿದ್ದರೆ ದಾರಿ ತಾನಾಗಿ ತೋಚುತ್ತದೆ",
        "ಜ್ಞಾನ ಅಮೃತದಂತೆ — ಕುಡಿದಷ್ಟೂ ಮತ್ತೆ ಬೇಕು",
        "ಇಂದಿನ ಪ್ರಯತ್ನ ನಾಳಿನ ಯಶಸ್ಸು",
        "ಓದು ಬೆಳಕು — ಅಜ್ಞಾನ ಕತ್ತಲು",
        "ಒಂದೊಂದು ಅಧ್ಯಾಯ — ಒಂದೊಂದು ಗೆಲುವು",
        "ಕಷ್ಟಪಟ್ಟು ಓದಿದವರು ಖಂಡಿತ ಗೆಲ್ಲುತ್ತಾರೆ",
        "ನಿಮ್ಮ ಕನಸನ್ನು ವಿದ್ಯೆ ಮಾತ್ರ ನನಸು ಮಾಡಬಲ್ಲದು"
    )
    fun quotes(isEnglish: Boolean) = if (isEnglish) QUOTES_EN else QUOTES_KN
}
