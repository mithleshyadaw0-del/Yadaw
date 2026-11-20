package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object NavodayaSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ---------------- CLASS 6: FULL LENGTH ----------------
        // Note: test_jnv_sainik_03 in SeedExamData serves as Navodaya Class 6 Full Length Mock Test 01
        makeTest(
            id = "jnv_c6_fl_pyq",
            title = "JNVST Class 6 - Previous Year Paper (PYQ)",
            titleHindi = "नवोदय कक्षा 6 - पिछले वर्ष का प्रश्न पत्र",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_fl_model",
            title = "JNVST Class 6 - Model Paper 01",
            titleHindi = "नवोदय कक्षा 6 - मॉडल पेपर 01",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Model Paper",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_fl_practice",
            title = "JNVST Class 6 - Practice Test 01",
            titleHindi = "नवोदय कक्षा 6 - अभ्यास टेस्ट 01",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Practice Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.25
        ),

        // ---------------- CLASS 6: CHAPTER WISE ----------------
        makeTest(
            id = "jnv_c6_cw_math_num",
            title = "Number System & Place Value",
            titleHindi = "संख्या पद्धति एवं स्थानीय मान",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Number System (संख्या पद्धति)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_cw_math_frac",
            title = "Fractions, LCM & HCF Practice",
            titleHindi = "भिन्न, ल.स. एवं म.स. अभ्यास",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Fractions & LCM-HCF (भिन्न व ल.स.)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_cw_mat_series",
            title = "Analogy & Odd One Out",
            titleHindi = "सादृश्यता एवं असंगत छांटना",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Reasoning / Mental Ability",
            chapter = "Odd Man Out & Analogy (असंगत व सादृश्यता)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_cw_hin_gram",
            title = "Hindi Grammar & Vocabulary",
            titleHindi = "हिन्दी व्याकरण, विलोम एवं पर्यायवाची",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Hindi / Language",
            chapter = "पर्यायवाची व विलोम शब्द",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),

        // ---------------- CLASS 6: SECTIONAL ----------------
        makeTest(
            id = "jnv_c6_sec_mat",
            title = "Mental Ability Sectional Test",
            titleHindi = "मानसिक योग्यता सेक्शनल टेस्ट (Class 6)",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_sec_math",
            title = "Arithmetic Mathematics Sectional Test",
            titleHindi = "अंकगणित सेक्शनल टेस्ट (Class 6)",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_sec_hin",
            title = "Hindi Language Sectional Test",
            titleHindi = "हिन्दी भाषा परीक्षण सेक्शनल टेस्ट (Class 6)",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Hindi / Language",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.25
        ),

        // ---------------- CLASS 9: FULL LENGTH ----------------
        makeTest(
            id = "jnv_c9_fl_mock",
            title = "JNVST Class 9 - Complete Mock Test 01",
            titleHindi = "नवोदय कक्षा 9 - पूर्ण मॉक टेस्ट 01",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_fl_pyq",
            title = "JNVST Class 9 - Previous Year Paper",
            titleHindi = "नवोदय कक्षा 9 - पिछले वर्ष का पेपर",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_fl_model",
            title = "JNVST Class 9 - Model Paper 01",
            titleHindi = "नवोदय कक्षा 9 - मॉडल पेपर 01",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Model Paper",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),

        // ---------------- CLASS 9: CHAPTER WISE ----------------
        makeTest(
            id = "jnv_c9_cw_math_rat",
            title = "Rational Numbers & Exponents",
            titleHindi = "परिमेय संख्याएं एवं घातांक (Class 9)",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Rational Numbers & Powers (परिमेय संख्याएं)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_cw_math_alg",
            title = "Squares, Cubes & Algebraic Identities",
            titleHindi = "वर्ग, घन व बीजीय सर्वसमिकाएं (Class 9)",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Algebra & Squares-Cubes (बीजगणित व वर्ग)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_cw_sci_force",
            title = "Force, Pressure & Friction",
            titleHindi = "बल, दाब एवं घर्षण (Class 9)",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Science",
            chapter = "Force, Pressure & Light (बल व दाब)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_cw_eng_gram",
            title = "Tense, Voice & Narration",
            titleHindi = "अंग्रेजी व्याकरण - Tense व Voice (Class 9)",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "English",
            chapter = "Grammar & Voice (व्याकरण)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_cw_hin_sandhi",
            title = "संधि, समास एवं मुहावरे (Class 9)",
            titleHindi = "वर्ण, संधि एवं समास अभ्यास",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Hindi / Language",
            chapter = "संधि, समास व वाक्य शुद्धि",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),

        // ---------------- CLASS 9: SECTIONAL ----------------
        makeTest(
            id = "jnv_c9_sec_math",
            title = "Class 9 Mathematics Sectional Test",
            titleHindi = "कक्षा 9 गणित सेक्शनल टेस्ट",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_sec_sci",
            title = "Class 9 Science Sectional Test",
            titleHindi = "कक्षा 9 विज्ञान सेक्शनल टेस्ट",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Science",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_sec_eng",
            title = "Class 9 English Sectional Test",
            titleHindi = "कक्षा 9 अंग्रेजी सेक्शनल टेस्ट",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "English",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_sec_hin",
            title = "Class 9 Hindi Sectional Test",
            titleHindi = "कक्षा 9 हिन्दी सेक्शनल टेस्ट",
            category = "Navodaya",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Hindi / Language",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "jnv_c6_fl_pyq", "jnv_c6_fl_practice" -> listOf(
                SeedQ("Which is the greatest 4-digit even number formed by digits 3, 0, 5, 8?", "अंकों 3, 0, 5, 8 से बनने वाली सबसे बड़ी 4 अंकों की सम संख्या कौन सी है?", "8530", "8503", "8350", "8053", "A", "Largest arrangement ending in even digit 0 or 8 is 8530.", "सबसे बड़ा क्रम जिसके अंत में सम अंक हो: 8530।"),
                SeedQ("Find the odd term: 16, 25, 36, 48", "असंगत संख्या चुनिए: 16, 25, 36, 48", "16", "25", "36", "48", "D", "16, 25, 36 are perfect squares; 48 is not.", "16, 25, 36 पूर्ण वर्ग हैं जबकि 48 नहीं।"),
                SeedQ("A train leaves at 7:45 AM and arrives at 1:15 PM. Find journey time.", "एक रेलगाड़ी सुबह 7:45 बजे चलकर दोपहर 1:15 बजे पहुँचती है। यात्रा समय बताएं।", "5 hr 15 min", "5 hr 30 min", "6 hr 30 min", "4 hr 30 min", "B", "7:45 to 12:00 = 4h 15m + 1h 15m = 5h 30m.", "7:45 से 1:15 = 5 घंटे 30 मिनट।"),
                SeedQ("'उन्नति' का सही विलोम शब्द क्या है?", "'उन्नति' का सही विलोम शब्द क्या है?", "प्रगति", "अवनति", "उत्कर्ष", "सफलता", "B", "अवनति is antonym of उन्नति.", "उन्नति का विलोम 'अवनति' होता है।"),
                SeedQ("What is the value of 3/4 + 1/8?", "3/4 + 1/8 का मान क्या होगा?", "4/12", "7/8", "5/8", "1/2", "B", "6/8 + 1/8 = 7/8.", "6/8 + 1/8 = 7/8।")
            )
            "jnv_c6_fl_model", "jnv_c6_sec_math" -> listOf(
                SeedQ("Find the sum of prime numbers between 10 and 20.", "10 और 20 के बीच सभी अभाज्य संख्याओं का योग कितना है?", "60", "58", "62", "55", "A", "Primes: 11 + 13 + 17 + 19 = 60.", "अभाज्य संख्याएं: 11+13+17+19 = 60।"),
                SeedQ("If 20% of a number is 40, find the number.", "यदि किसी संख्या का 20% भाग 40 है, तो वह संख्या क्या है?", "160", "180", "200", "240", "C", "20% = 40 => 100% = 200.", "संख्या = (40 × 100)/20 = 200।"),
                SeedQ("Perimeter of a square of side 12 cm is:", "12 सेमी भुजा वाले वर्ग का परिमाप कितना होगा?", "36 cm", "48 cm", "144 cm", "24 cm", "B", "4 × 12 = 48 cm.", "परिमाप = 4 × 12 = 48 सेमी।"),
                SeedQ("Cost of 1 dozen bananas is ₹60. Cost of 5 bananas is:", "1 दर्जन केलों का मूल्य ₹60 है, तो 5 केलों का मूल्य होगा:", "₹20", "₹25", "₹30", "₹35", "B", "1 banana = 60/12 = ₹5. 5 bananas = ₹25.", "1 केला = ₹5, 5 केले = ₹25।"),
                SeedQ("Evaluate: 15 × 4 ÷ 2 + 10", "सरल करें: 15 × 4 ÷ 2 + 10", "30", "40", "45", "50", "B", "BODMAS: 15 × 2 + 10 = 30 + 10 = 40.", "15 × 2 + 10 = 40।")
            )
            "jnv_c6_cw_math_num" -> listOf(
                SeedQ("Place value of 7 in 47,892 is:", "संख्या 47,892 में 7 का स्थानीय मान क्या है?", "70", "700", "7000", "7", "C", "7 is in thousands place => 7000.", "7 हजार के स्थान पर है अतः 7000।"),
                SeedQ("Difference between greatest 4-digit and smallest 4-digit number is:", "4 अंकों की सबसे बड़ी व सबसे छोटी संख्या का अंतर है:", "8999", "9000", "8990", "9999", "A", "9999 - 1000 = 8999.", "9999 - 1000 = 8999।"),
                SeedQ("Which number is divisible by 9?", "निम्न में से कौन सी संख्या 9 से विभाज्य है?", "1234", "2349", "4512", "3121", "B", "Sum of digits 2+3+4+9=18, divisible by 9.", "अंकों का योग 18 है जो 9 से भाज्य है।"),
                SeedQ("Roman numeral for 95 is:", "95 को रोमन अंक में कैसे लिखा जाता है?", "XCV", "CXV", "LXLV", "VC", "A", "XC (90) + V (5) = XCV.", "90 (XC) + 5 (V) = XCV।"),
                SeedQ("What is the predecessor of 10,000?", "10,000 की पूर्ववर्ती संख्या क्या है?", "9,999", "10,001", "9,990", "9,900", "A", "10000 - 1 = 9999.", "10000 - 1 = 9999।")
            )
            "jnv_c6_cw_math_frac" -> listOf(
                SeedQ("HCF of 24 and 36 is:", "24 और 36 का म.स. (HCF) क्या है?", "6", "12", "18", "72", "B", "Largest common factor of 24 and 36 is 12.", "24 व 36 का म.स. = 12।"),
                SeedQ("LCM of 8, 12, 16 is:", "8, 12 और 16 का ल.स. (LCM) ज्ञात करें:", "24", "36", "48", "96", "C", "48 is divisible by 8, 12, and 16.", "48 सबसे छोटा गुणज है।"),
                SeedQ("Which fraction is largest: 1/2, 2/3, 3/4, 1/4?", "इनमें सबसे बड़ी भिन्न कौन सी है: 1/2, 2/3, 3/4, 1/4?", "1/2", "2/3", "3/4", "1/4", "C", "3/4 = 0.75 is the greatest.", "3/4 = 0.75 सबसे बड़ी है।"),
                SeedQ("Reciprocal of 5/7 is:", "5/7 का व्युत्क्रम (Reciprocal) क्या है?", "5/7", "7/5", "2/7", "1/5", "B", "Flip numerator and denominator => 7/5.", "अंश व हर बदलने पर 7/5।"),
                SeedQ("Convert 0.25 into simplest fraction.", "0.25 को सरलतम भिन्न में बदलें।", "1/2", "1/4", "1/5", "2/5", "B", "25/100 = 1/4.", "25/100 = 1/4।")
            )
            "jnv_c6_cw_mat_series", "jnv_c6_sec_mat" -> listOf(
                SeedQ("Find the next number: 2, 6, 12, 20, 30, ?", "अगली संख्या ज्ञात करें: 2, 6, 12, 20, 30, ?", "38", "40", "42", "44", "C", "Differences are +4, +6, +8, +10, +12 => 30 + 12 = 42.", "अंतर +4, +6, +8, +10, +12 है अतः 42।"),
                SeedQ("Odd one out: Apple, Mango, Potato, Orange", "असंगत शब्द चुनिए: सेब, आम, आलू, संतरा", "Apple", "Mango", "Potato", "Orange", "C", "Potato is a vegetable; others are fruits.", "आलू सब्जी है, बाकी फल हैं।"),
                SeedQ("Doctor : Hospital :: Teacher : ?", "डॉक्टर : अस्पताल :: शिक्षक : ?", "Court", "School", "Bank", "Station", "B", "Teacher works at School.", "शिक्षक विद्यालय में कार्य करते हैं।"),
                SeedQ("Find missing letter: A, C, F, J, ?", "लुप्त अक्षर ज्ञात करें: A, C, F, J, ?", "M", "N", "O", "P", "C", "+2, +3, +4, +5 => J(10)+5 = O(15).", "अंतर +2, +3, +4, +5 => O।"),
                SeedQ("Odd one out: 121, 144, 169, 175", "असंगत चुनें: 121, 144, 169, 175", "121", "144", "169", "175", "D", "175 is not a square; others are 11², 12², 13².", "175 पूर्ण वर्ग नहीं है।")
            )
            "jnv_c6_cw_hin_gram", "jnv_c6_sec_hin" -> listOf(
                SeedQ("'सूर्य' का पर्यायवाची शब्द कौन सा है?", "'सूर्य' का पर्यायवाची शब्द कौन सा है?", "राकेश", "दिनकर", "नीरज", "पवन", "B", "दिनकर means Sun.", "'दिनकर' सूर्य का पर्यायवाची है।"),
                SeedQ("'ईमानदार' शब्द का विलोम क्या होगा?", "'ईमानदार' शब्द का विलोम क्या होगा?", "सच्चा", "बेईमान", "परिश्रमी", "दयालु", "B", "बेईमान is opposite of ईमानदार.", "ईमानदार का विलोम 'बेईमान' है।"),
                SeedQ("'जो सब कुछ जानता हो' के लिए एक शब्द है:", "'जो सब कुछ जानता हो' के लिए एक शब्द है:", "अल्पज्ञ", "सर्वज्ञ", "अज्ञानी", "विद्वान", "B", "सर्वज्ञ means one who knows everything.", "सब कुछ जानने वाला 'सर्वज्ञ' कहलाता है।"),
                SeedQ("शुद्ध वर्तनी वाला शब्द चुनें:", "शुद्ध वर्तनी वाला शब्द चुनें:", "आशिर्वाद", "आशीर्वाद", "आशीरवाद", "अशीर्वाद", "B", "आशीर्वाद is the correct spelling.", "सही वर्तनी 'आशीर्वाद' है।"),
                SeedQ("'हाथ बंटाना' मुहावरे का क्या अर्थ है?", "'हाथ बंटाना' मुहावरे का क्या अर्थ है?", "लड़ाई करना", "मदद करना", "चोरी करना", "भाग जाना", "B", "हाथ बंटाना means to help.", "हाथ बंटाना का अर्थ 'मदद करना' है।")
            )
            // ---------------- CLASS 9 ----------------
            "jnv_c9_fl_mock", "jnv_c9_fl_pyq", "jnv_c9_fl_model", "jnv_c9_sec_math" -> listOf(
                SeedQ("Value of (2⁻³ × 2⁵) is:", "(2⁻³ × 2⁵) का मान क्या है?", "2", "4", "8", "16", "B", "2^(-3+5) = 2² = 4.", "2^(5-3) = 2² = 4।"),
                SeedQ("Solve for x: 3x - 7 = 2x + 5", "x का मान ज्ञात करें: 3x - 7 = 2x + 5", "10", "11", "12", "14", "C", "3x - 2x = 5 + 7 => x = 12.", "x = 5 + 7 = 12।"),
                SeedQ("Cube root of 1728 is:", "1728 का घनमूल (Cube root) कितना है?", "11", "12", "13", "14", "B", "12 × 12 × 12 = 1728.", "12³ = 1728।"),
                SeedQ("Sum of exterior angles of any polygon is:", "किसी भी बहुभुज के बाह्य कोणों का योग होता है:", "180°", "270°", "360°", "540°", "C", "Always 360°.", "सदैव 360° होता है।"),
                SeedQ("Volume of a cube of side 5 cm is:", "5 सेमी भुजा वाले घन का आयतन क्या होगा?", "25 cm³", "100 cm³", "125 cm³", "150 cm³", "C", "a³ = 5³ = 125 cm³.", "भुजा³ = 5³ = 125 सेमी³।")
            )
            "jnv_c9_cw_math_rat" -> listOf(
                SeedQ("Multiplicative inverse of -5/8 is:", "-5/8 का गुणात्मक प्रतिलोम क्या है?", "5/8", "8/5", "-8/5", "0", "C", "Product with -8/5 equals 1.", "-5/8 × (-8/5) = 1।"),
                SeedQ("Additive inverse of 3/7 is:", "3/7 का योज्य प्रतिलोम क्या है?", "7/3", "-3/7", "-7/3", "1", "B", "3/7 + (-3/7) = 0.", "3/7 का योज्य प्रतिलोम -3/7।"),
                SeedQ("Value of (5⁰ + 3⁰) × 2⁰ is:", "(5⁰ + 3⁰) × 2⁰ का मान है:", "0", "1", "2", "10", "C", "(1 + 1) × 1 = 2.", "(1 + 1) × 1 = 2।"),
                SeedQ("Express 0.00054 in standard form:", "0.00054 को मानक रूप में लिखें:", "5.4 × 10⁻⁴", "5.4 × 10⁻³", "54 × 10⁻⁴", "5.4 × 10⁴", "A", "Shift decimal 4 places right => 5.4 × 10⁻⁴.", "5.4 × 10⁻⁴।"),
                SeedQ("If 2ˣ = 64, then x =", "यदि 2ˣ = 64 हो, तो x का मान होगा:", "5", "6", "7", "8", "B", "2⁶ = 64 => x = 6.", "2⁶ = 64 अतः x = 6।")
            )
            "jnv_c9_cw_math_alg" -> listOf(
                SeedQ("Expand: (x + 5)²", "विस्तार करें: (x + 5)²", "x² + 25", "x² + 5x + 25", "x² + 10x + 25", "x² + 10x + 10", "C", "(a+b)² = a² + 2ab + b².", "x² + 10x + 25।"),
                SeedQ("Factorize: x² - 49", "गुणनखंड करें: x² - 49", "(x-7)(x-7)", "(x+7)(x-7)", "(x+49)(x-1)", "(x+7)²", "B", "a² - b² = (a+b)(a-b).", "(x+7)(x-7)।"),
                SeedQ("Square root of 2025 is:", "2025 का वर्गमूल ज्ञात करें:", "35", "45", "55", "65", "B", "45 × 45 = 2025.", "45² = 2025।"),
                SeedQ("If x - 1/x = 5, find x² + 1/x².", "यदि x - 1/x = 5 हो, तो x² + 1/x² =", "23", "25", "27", "29", "C", "5² + 2 = 27.", "5² + 2 = 27।"),
                SeedQ("How many non-square numbers lie between 9² and 10²?", "9² और 10² के बीच कितनी गैर-वर्ग संख्याएं हैं?", "16", "18", "19", "20", "B", "2n = 2 × 9 = 18.", "2n = 2 × 9 = 18।")
            )
            "jnv_c9_cw_sci_force", "jnv_c9_sec_sci" -> listOf(
                SeedQ("SI unit of Force is:", "बल (Force) का SI मात्रक क्या है?", "Joule", "Newton", "Pascal", "Watt", "B", "Newton is the SI unit of force.", "बल का SI मात्रक न्यूटन है।"),
                SeedQ("SI unit of Pressure is:", "दाब (Pressure) का SI मात्रक क्या है?", "Newton", "Pascal", "Dyne", "Hertz", "B", "Pressure = Force/Area => Pascal.", "दाब का मात्रक पास्कल है।"),
                SeedQ("Which microorganism causes Malaria?", "मलेरिया किस सूक्ष्मजीव से होता है?", "Bacteria", "Virus", "Protozoa", "Fungus", "C", "Plasmodium is a protozoan.", "प्लाज्मोडियम प्रोटोजोआ द्वारा।"),
                SeedQ("Speed of sound is maximum in:", "ध्वनि की चाल सबसे अधिक किसमें होती है?", "Vacuum", "Air", "Water", "Solids", "D", "Sound travels fastest in solids.", "ठोस में ध्वनि की गति सर्वाधिक होती है।"),
                SeedQ("Which metal is liquid at room temperature?", "कौन सी धातु कमरे के तापमान पर द्रव होती है?", "Sodium", "Mercury", "Iron", "Copper", "B", "Mercury (पारा) is liquid metal.", "पारा (Mercury) द्रव धातु है।")
            )
            "jnv_c9_cw_eng_gram", "jnv_c9_sec_eng" -> listOf(
                SeedQ("Choose the correct passive voice: 'Ram writes a letter.'", "सही Passive Voice चुनें: 'Ram writes a letter.'", "A letter was written by Ram.", "A letter is written by Ram.", "A letter is writing by Ram.", "A letter has written by Ram.", "B", "Present simple passive uses is/am/are + V3.", "is written by Ram."),
                SeedQ("Synonym of 'Brave' is:", "'Brave' का समानार्थी (Synonym) शब्द है:", "Coward", "Courageous", "Weak", "Timid", "B", "Courageous means brave.", "Courageous का अर्थ साहसी होता है।"),
                SeedQ("Fill in the blank: He ___ to school daily.", "रिक्त स्थान भरें: He ___ to school daily.", "go", "goes", "going", "gone", "B", "Singular subject takes goes in present simple.", "एकवचन कर्ता के साथ goes लगता है।"),
                SeedQ("Antonym of 'Ancient' is:", "'Ancient' का विलोम शब्द है:", "Old", "Modern", "Historic", "Past", "B", "Modern is opposite of Ancient.", "Ancient (प्राचीन) का विलोम Modern है।"),
                SeedQ("Correct spelling is:", "सही स्पेलिंग चुनें:", "Recieve", "Receive", "Receeve", "Recive", "B", "i before e except after c => Receive.", "Receive सही है।")
            )
            "jnv_c9_cw_hin_sandhi", "jnv_c9_sec_hin" -> listOf(
                SeedQ("'विद्यालय' शब्द में कौन सी संधि है?", "'विद्यालय' शब्द में कौन सी संधि है?", "गुण संधि", "दीर्घ स्वर संधि", "यण संधि", "व्यंजन संधि", "B", "विद्या + आलय = दीर्घ संधि।", "आ + आ = आ (दीर्घ संधि)।"),
                SeedQ("'रसोईघर' में कौन सा समास है?", "'रसोईघर' में कौन सा समास है?", "द्वंद्व", "तत्पुरुष", "बहुव्रीहि", "अव्ययीभाव", "B", "रसोई के लिए घर = संप्रदान तत्पुरुष।", "तत्पुरुष समास।"),
                SeedQ("'ईद का चाँद होना' मुहावरे का अर्थ है:", "'ईद का चाँद होना' मुहावरे का अर्थ है:", "बहुत सुंदर होना", "बहुत दिनों बाद दिखाई देना", "खुश होना", "गायब होना", "B", "बहुत दिनों बाद दिखना।", "बहुत दिनों बाद दिखाई देना।"),
                SeedQ("हिन्दी में कुल कितने स्वर होते हैं?", "मानक हिन्दी में स्वरों की संख्या कितनी मानी जाती है?", "10", "11", "13", "33", "B", "11 मूल स्वर होते हैं।", "11 स्वर।"),
                SeedQ("'कमल' का पर्यायवाची नहीं है:", "निम्न में से कौन 'कमल' का पर्यायवाची नहीं है?", "पंकज", "जलज", "नीरज", "जलद", "D", "जलद बादल का पर्यायवाची है।", "जलद बादल को कहते हैं।")
            )
            else -> return null
        }
        val marks = if (testId.startsWith("jnv_c6")) 1.25 else 1.0
        return buildQuestionPack(testId, marks, items)
    }
}
