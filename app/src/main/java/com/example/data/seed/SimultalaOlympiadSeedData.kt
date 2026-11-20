package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object SimultalaOlympiadSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ============================================================
        // SIMULTALA AWASIYA VIDYALAYA (SAV BIHAR) - CLASS 6 ONLY
        // ============================================================
        makeTest(
            id = "sim_c6_fl_mock",
            title = "Simultala Class 6 - Complete Prelims Mock 01",
            titleHindi = "सिमुलतला आवासीय विद्यालय कक्षा 6 - पूर्ण मॉक टेस्ट 01",
            category = "Simultala",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_fl_pyq",
            title = "Simultala Class 6 - Previous Year Paper (PYQ)",
            titleHindi = "सिमुलतला कक्षा 6 - विगत वर्ष का प्रश्न पत्र",
            category = "Simultala",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_fl_model",
            title = "Simultala Class 6 - Model Paper 01",
            titleHindi = "सिमुलतला कक्षा 6 - मॉडल पेपर 01",
            category = "Simultala",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Model Paper",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_fl_practice",
            title = "Simultala Class 6 - Practice Test 01",
            titleHindi = "सिमुलतला कक्षा 6 - अभ्यास टेस्ट 01",
            category = "Simultala",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Practice Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        // Chapter Wise (Simultala Class 6)
        makeTest(
            id = "sim_c6_cw_math_pct",
            title = "Percentage, Profit & Simple Interest",
            titleHindi = "प्रतिशत, लाभ-हानि व साधारण ब्याज",
            category = "Simultala",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Percentage & Simple Interest (प्रतिशत व ब्याज)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_cw_sci_plant",
            title = "Plants, Nutrition & Human Body",
            titleHindi = "पौधे, पोषण एवं मानव शरीर",
            category = "Simultala",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Science",
            chapter = "Nutrition & Human Body (पोषण व शरीर)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_cw_gk_bihar",
            title = "Bihar Heritage & Indian Geography",
            titleHindi = "बिहार सामान्य ज्ञान एवं भारतीय भूगोल",
            category = "Simultala",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "General Knowledge",
            chapter = "Bihar & Geography (बिहार व भूगोल)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_cw_hin_karak",
            title = "संज्ञा, सर्वनाम, विशेषण व कारक",
            titleHindi = "हिन्दी व्याकरण - संज्ञा, सर्वनाम व कारक",
            category = "Simultala",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Hindi / Language",
            chapter = "संज्ञा, सर्वनाम व कारक",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        // Sectional (Simultala Class 6)
        makeTest(
            id = "sim_c6_sec_math",
            title = "Simultala Mathematics Sectional Test",
            titleHindi = "सिमुलतला गणित सेक्शनल टेस्ट (Class 6)",
            category = "Simultala",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_sec_sci",
            title = "Simultala Science Sectional Test",
            titleHindi = "सिमुलतला विज्ञान सेक्शनल टेस्ट (Class 6)",
            category = "Simultala",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Science",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_sec_gk",
            title = "Simultala Social Studies / GK Sectional Test",
            titleHindi = "सिमुलतला सामाजिक अध्ययन सेक्शनल टेस्ट",
            category = "Simultala",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "General Knowledge",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_sec_hin",
            title = "Simultala Hindi Language Sectional Test",
            titleHindi = "सिमुलतला हिन्दी भाषा सेक्शनल टेस्ट",
            category = "Simultala",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Hindi / Language",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),

        // ============================================================
        // OLYMPIAD (IMO / NSO) - CLASS 6 & CLASS 9 (DB BACKED)
        // ============================================================
        // Class 6 Olympiad
        makeTest(
            id = "oly_c6_fl_mock",
            title = "Maths & Science Olympiad Class 6 - Mock 01",
            titleHindi = "ओलंपियाड कक्षा 6 - पूर्ण मॉक टेस्ट 01",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_fl_pyq",
            title = "IMO Olympiad Class 6 - Previous Year Paper",
            titleHindi = "गणित ओलंपियाड कक्षा 6 - विगत वर्ष पेपर",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_cw_math_logic",
            title = "Number Puzzles & Cryptarithms",
            titleHindi = "संख्या पहेलियाँ एवं तार्किक गणित",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Number Puzzles & Patterns (संख्या पहेलियाँ)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_cw_reas_log",
            title = "Mirror Images, Coding & Ranking",
            titleHindi = "दर्पण प्रतिबिंब, कोडिंग व रैंकिंग",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Reasoning / Mental Ability",
            chapter = "Logical Reasoning & Mirror (तार्किक योग्यता)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0
        ),
        makeTest(
            id = "oly_c6_sec_math",
            title = "Olympiad Class 6 Mathematics Sectional",
            titleHindi = "ओलंपियाड कक्षा 6 गणित सेक्शनल टेस्ट",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_sec_sci",
            title = "Olympiad Class 6 Science (NSO) Sectional",
            titleHindi = "ओलंपियाड कक्षा 6 विज्ञान सेक्शनल टेस्ट",
            category = "Olympiad",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Science",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),

        // Class 9 Olympiad
        makeTest(
            id = "oly_c9_fl_mock",
            title = "IMO & NSO Olympiad Class 9 - Mock Test 01",
            titleHindi = "ओलंपियाड कक्षा 9 - पूर्ण मॉक टेस्ट 01",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_fl_practice",
            title = "Olympiad Class 9 - Achievers Practice Test",
            titleHindi = "ओलंपियाड कक्षा 9 - उच्च स्तरीय अभ्यास टेस्ट",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Practice Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_cw_math_poly",
            title = "Polynomials, Remainder & Coordinate Geom",
            titleHindi = "बहुपद, शेषफल प्रमेय व निर्देशांक ज्यामिति",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Polynomials & Geometry (बहुपद व ज्यामिति)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_cw_sci_laws",
            title = "Newton's Laws, Work & Gravitation",
            titleHindi = "न्यूटन के गति नियम, कार्य व गुरुत्वाकर्षण",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Science",
            chapter = "Laws of Motion & Gravitation (गति के नियम)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_sec_math",
            title = "Olympiad Class 9 Mathematics Sectional",
            titleHindi = "ओलंपियाड कक्षा 9 गणित सेक्शनल टेस्ट",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_sec_reas",
            title = "Olympiad Class 9 Logical Reasoning Sectional",
            titleHindi = "ओलंपियाड कक्षा 9 तार्किक शक्ति सेक्शनल टेस्ट",
            category = "Olympiad",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "sim_c6_fl_mock", "sim_c6_fl_pyq", "sim_c6_fl_model", "sim_c6_fl_practice" -> listOf(
                SeedQ("Where is Simultala Awasiya Vidyalaya located in Bihar?", "बिहार में सिमुलतला आवासीय विद्यालय किस जिले में स्थित है?", "Patna", "Jamui", "Gaya", "Nalanda", "B", "Simultala is in Jamui district, Bihar.", "सिमुलतला आवासीय विद्यालय जमुई जिले में है।"),
                SeedQ("What is 25% of ₹800?", "₹800 का 25% कितना होगा?", "₹150", "₹200", "₹250", "₹300", "B", "800 × 25/100 = ₹200.", "800 का 1/4 = ₹200।"),
                SeedQ("Which vitamin is obtained mainly from sunlight?", "सूर्य के प्रकाश से मुख्यतः कौन सा विटामिन प्राप्त होता है?", "Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D", "D", "Sunlight helps skin produce Vitamin D.", "सूर्य की रोशनी से विटामिन D मिलता है।"),
                SeedQ("'आँख का तारा' मुहावरे का सही अर्थ क्या है?", "'आँख का तारा' मुहावरे का सही अर्थ क्या है?", "बहुत दुखी", "अत्यंत प्रिय", "अंधा होना", "गुस्सा होना", "B", "अत्यंत प्रिय।", "बहुत प्यारा / अत्यंत प्रिय।"),
                SeedQ("Find the area of a square whose perimeter is 36 cm.", "जिस वर्ग का परिमाप 36 सेमी है, उसका क्षेत्रफल ज्ञात करें।", "36 cm²", "64 cm²", "81 cm²", "108 cm²", "C", "Side = 36/4 = 9 cm. Area = 9² = 81 cm².", "भुजा = 9 सेमी, क्षेत्रफल = 81 सेमी²।")
            )
            "sim_c6_cw_math_pct", "sim_c6_sec_math" -> listOf(
                SeedQ("Find simple interest on ₹2,000 at 5% per annum for 4 years.", "₹2,000 पर 5% वार्षिक दर से 4 वर्ष का साधारण ब्याज ज्ञात करें।", "₹300", "₹400", "₹500", "₹600", "B", "(2000 × 5 × 4)/100 = ₹400.", "साधारण ब्याज = ₹400।"),
                SeedQ("CP = ₹400, SP = ₹460. Find Profit %.", "क्रय मूल्य ₹400 और विक्रय मूल्य ₹460 है, तो लाभ % ज्ञात करें।", "12%", "15%", "18%", "20%", "B", "(60 / 400) × 100 = 15%.", "लाभ % = (60/400) × 100 = 15%।"),
                SeedQ("Convert 0.45 into percentage.", "दशमलव 0.45 को प्रतिशत में बदलें।", "4.5%", "45%", "0.45%", "450%", "B", "0.45 × 100 = 45%.", "0.45 × 100 = 45%।"),
                SeedQ("If 3/4 of a number is 48, find the number.", "यदि किसी संख्या का 3/4 भाग 48 है, तो संख्या ज्ञात करें।", "56", "60", "64", "72", "C", "48 × 4/3 = 64.", "48 × 4 / 3 = 64।"),
                SeedQ("Average of first 5 natural numbers is:", "प्रथम 5 प्राकृत संख्याओं का औसत क्या है?", "2", "3", "4", "5", "B", "(1+2+3+4+5)/5 = 15/5 = 3.", "15 / 5 = 3।")
            )
            "sim_c6_cw_sci_plant", "sim_c6_sec_sci" -> listOf(
                SeedQ("Green pigment in leaves used for photosynthesis is:", "पत्तियों में प्रकाश संश्लेषण के लिए कौन सा हरा वर्णक होता है?", "Hemoglobin", "Chlorophyll", "Starch", "Glucose", "B", "Chlorophyll traps sunlight.", "क्लोरोफिल (पर्णहरित)।"),
                SeedQ("How many bones are in an adult human body?", "एक वयस्क मानव शरीर में कुल कितनी हड्डियाँ होती हैं?", "200", "206", "212", "300", "B", "206 bones in an adult human.", "206 हड्डियाँ।"),
                SeedQ("Deficiency of Vitamin C causes:", "विटामिन C की कमी से कौन सा रोग होता है?", "Rickets", "Scurvy", "Beriberi", "Goitre", "B", "Scurvy (bleeding gums).", "स्कर्वी रोग।"),
                SeedQ("Boiling point of pure water at sea level is:", "शुद्ध जल का क्वथनांक (Boiling point) कितना होता है?", "0°C", "50°C", "100°C", "120°C", "C", "Water boils at 100°C.", "100°C।"),
                SeedQ("Which gas do plants absorb during photosynthesis?", "प्रकाश संश्लेषण के दौरान पौधे कौन सी गैस ग्रहण करते हैं?", "Oxygen", "Nitrogen", "Carbon dioxide", "Helium", "C", "Plants take in CO₂.", "कार्बन डाइऑक्साइड।")
            )
            "sim_c6_cw_gk_bihar", "sim_c6_sec_gk" -> listOf(
                SeedQ("Which river is known as the 'Sorrow of Bihar'?", "किस नदी को 'बिहार का शोक' कहा जाता है?", "Ganga", "Kosi", "Son", "Gandak", "B", "Kosi river frequently shifts course and causes floods.", "कोसी नदी को बिहार का शोक कहते हैं।"),
                SeedQ("Where did Lord Buddha attain enlightenment?", "भगवान बुद्ध को ज्ञान की प्राप्ति कहाँ हुई थी?", "Vaishali", "Bodh Gaya", "Rajgir", "Sarnath", "B", "Under the Bodhi tree in Bodh Gaya.", "बोधगया में ज्ञान प्राप्त हुआ।"),
                SeedQ("State tree of Bihar is:", "बिहार का राजकीय वृक्ष कौन सा है?", "Banyan", "Peepal", "Mango", "Neem", "B", "Peepal tree.", "पीपल।"),
                SeedQ("Ancient Nalanda University is located in:", "प्राचीन नालंदा विश्वविद्यालय किस राज्य में है?", "Uttar Pradesh", "Bihar", "Jharkhand", "Odisha", "B", "Located in Nalanda, Bihar.", "बिहार में स्थित है।"),
                SeedQ("Largest state of India by area is:", "क्षेत्रफल की दृष्टि से भारत का सबसे बड़ा राज्य कौन सा है?", "Maharashtra", "Madhya Pradesh", "Rajasthan", "Uttar Pradesh", "C", "Rajasthan.", "राजस्थान।")
            )
            "sim_c6_cw_hin_karak", "sim_c6_sec_hin" -> listOf(
                SeedQ("'पेड़ से पत्ता गिरा' — वाक्य में कौन सा कारक है?", "'पेड़ से पत्ता गिरा' — वाक्य में कौन सा कारक है?", "करण कारक", "अपादान कारक", "संप्रदान कारक", "अधिकरण कारक", "B", "अलग होने के अर्थ में अपादान कारक होता है।", "अपादान कारक (अलगाव का भाव)।"),
                SeedQ("इनमें से कौन सा शब्द संज्ञा है?", "इनमें से कौन सा शब्द संज्ञा है?", "चलना", "सुंदर", "हिमालय", "वह", "C", "हिमालय व्यक्तिवाचक संज्ञा है।", "हिमालय संज्ञा शब्द है।"),
                SeedQ("सर्वनाम के मुख्य कितने भेद होते हैं?", "हिन्दी में सर्वनाम के मुख्य कितने भेद होते हैं?", "4", "5", "6", "8", "C", "6 भेद होते हैं।", "सर्वनाम के 6 भेद हैं।"),
                SeedQ("'नीला आकाश' में 'नीला' क्या है?", "'नीला आकाश' में 'नीला' शब्द व्याकरण की दृष्टि से क्या है?", "संज्ञा", "सर्वनाम", "विशेषण", "क्रिया", "C", "नीला गुणवाचक विशेषण है।", "विशेषण।"),
                SeedQ("राम ने रावण को मारा — 'ने' किस कारक का चिह्न है?", "'राम ने रावण को मारा' में 'ने' किस कारक की विभक्ति है?", "कर्ता", "कर्म", "करण", "संबंध", "A", "कर्ता ने।", "कर्ता कारक।")
            )
            // ---------------- OLYMPIAD CLASS 6 ----------------
            "oly_c6_fl_mock", "oly_c6_fl_pyq", "oly_c6_cw_math_logic", "oly_c6_sec_math" -> listOf(
                SeedQ("If A + A = 12 and A × B = 42, find A + B.", "यदि A + A = 12 और A × B = 42 हो, तो A + B ज्ञात करें।", "11", "13", "15", "17", "B", "A = 6, B = 42/6 = 7 => A + B = 13.", "A=6, B=7 => 6+7 = 13।"),
                SeedQ("How many triangles are formed by drawing 1 diagonal in a square?", "एक वर्ग में एक विकर्ण खींचने पर कितने त्रिभुज बनते हैं?", "1", "2", "3", "4", "B", "One diagonal divides a square into 2 triangles.", "2 त्रिभुज बनते हैं।"),
                SeedQ("Find the sum of first 10 positive even numbers.", "प्रथम 10 धनात्मक सम संख्याओं का योग कितना होता है?", "100", "110", "120", "90", "B", "n(n+1) = 10 × 11 = 110.", "10 × 11 = 110।"),
                SeedQ("What is the remainder when 10⁵ is divided by 9?", "10⁵ को 9 से भाग देने पर शेषफल क्या बचेगा?", "0", "1", "5", "8", "B", "10 ≡ 1 (mod 9) => 1⁵ = 1.", "शेषफल 1 बचेगा।"),
                SeedQ("A clock shows 3:00. What angle is formed between hour and minute hands?", "घड़ी में 3:00 बजे दोनों सुइयों के बीच कितना कोण बनता है?", "60°", "90°", "120°", "180°", "B", "3 × 30° = 90°.", "90° (समकोण)।")
            )
            "oly_c6_cw_reas_log" -> listOf(
                SeedQ("Which letter looks identical in a plane vertical mirror?", "समतल ऊर्ध्वाधर दर्पण में कौन सा अक्षर बिल्कुल समान दिखता है?", "B", "G", "M", "R", "C", "M is vertically symmetric.", "M का दर्पण प्रतिबिंब समान रहता है।"),
                SeedQ("In a row of 25 students, Neha is 9th from top. What is her rank from bottom?", "25 विद्यार्थियों की पंक्ति में नेहा ऊपर से 9वें स्थान पर है। नीचे से उसका स्थान क्या है?", "15", "16", "17", "18", "C", "25 - 9 + 1 = 17.", "25 - 9 + 1 = 17।"),
                SeedQ("If ROSE is coded as SPTF, how is MINT coded?", "यदि ROSE को SPTF लिखा जाए, तो MINT को क्या लिखेंगे?", "NJOU", "NJOV", "MKOU", "OJPU", "A", "Each letter +1 => M->N, I->J, N->O, T->U.", "प्रत्येक अक्षर +1: NJOU।"),
                SeedQ("Find missing number: 1, 4, 9, 16, ?, 36", "लुप्त संख्या बताएं: 1, 4, 9, 16, ?, 36", "20", "24", "25", "30", "C", "Squares of natural numbers: 5² = 25.", "5² = 25।"),
                SeedQ("Monday + 10 days equals which day?", "सोमवार के 10 दिन बाद कौन सा दिन होगा?", "Wednesday", "Thursday", "Friday", "Saturday", "B", "10 mod 7 = 3 days after Monday => Thursday.", "सोमवार + 3 दिन = गुरुवार।")
            )
            "oly_c6_sec_sci" -> listOf(
                SeedQ("Which device measures electric current?", "विद्युत धारा (Electric current) किस यंत्र से मापी जाती है?", "Voltmeter", "Ammeter", "Thermometer", "Barometer", "B", "Ammeter measures current in amperes.", "अमीटर (Ammeter)।"),
                SeedQ("Which component of food gives instant energy?", "भोजन का कौन सा घटक तुरंत ऊर्जा देता है?", "Protein", "Carbohydrate", "Vitamin", "Minerals", "B", "Carbohydrates (like glucose) give instant energy.", "कार्बोहाइड्रेट।"),
                SeedQ("Image formed by a plane mirror is always:", "समतल दर्पण द्वारा बना प्रतिबिंब सदैव होता है:", "Real & inverted", "Virtual & erect", "Magnified", "Diminished", "B", "Plane mirror forms virtual, erect, same-sized image.", "आभासी और सीधा।"),
                SeedQ("Unit of length best used to measure distance between cities is:", "दो शहरों के बीच की दूरी मापने का उपयुक्त मात्रक है:", "Millimeter", "Centimeter", "Kilometer", "Micron", "C", "Kilometer.", "किलोमीटर।"),
                SeedQ("Which material is a good conductor of electricity?", "निम्न में से कौन विद्युत का सुचालक है?", "Rubber", "Plastic", "Copper", "Dry wood", "C", "Copper is a metallic conductor.", "तांबा (Copper)।")
            )
            // ---------------- OLYMPIAD CLASS 9 ----------------
            "oly_c9_fl_mock", "oly_c9_fl_practice", "oly_c9_cw_math_poly", "oly_c9_sec_math" -> listOf(
                SeedQ("Find remainder when x³ - 3x² + 4 is divided by (x - 2).", "जब x³ - 3x² + 4 को (x - 2) से भाग दिया जाए तो शेषफल क्या होगा?", "0", "2", "4", "8", "A", "P(2) = 8 - 12 + 4 = 0.", "P(2) = 8 - 12 + 4 = 0।"),
                SeedQ("Point (-4, 5) lies in which quadrant?", "बिंदु (-4, 5) किस चतुर्थांश (Quadrant) में स्थित है?", "First", "Second", "Third", "Fourth", "B", "x < 0 and y > 0 lies in 2nd Quadrant.", "द्वितीय चतुर्थांश में।"),
                SeedQ("If a + b + c = 0, then a³ + b³ + c³ equals:", "यदि a + b + c = 0 हो, तो a³ + b³ + c³ बराबर होगा:", "0", "abc", "3abc", "2abc", "C", "Standard algebraic identity = 3abc.", "3abc।"),
                SeedQ("In triangle ABC, AB = AC and ∠B = 50°. Find ∠A.", "त्रिभुज ABC में AB = AC तथा ∠B = 50° है। ∠A ज्ञात करें:", "50°", "65°", "80°", "100°", "C", "∠C = 50°, so ∠A = 180 - 100 = 80°.", "180° - 100° = 80°।"),
                SeedQ("Value of (√5 + √3)(√5 - √3) is:", "(√5 + √3)(√5 - √3) का मान क्या है?", "2", "4", "8", "√2", "A", "5 - 3 = 2.", "5 - 3 = 2।")
            )
            "oly_c9_cw_sci_laws" -> listOf(
                SeedQ("Momentum of a body of mass m and velocity v is:", "m द्रव्यमान और v वेग वाली वस्तु का संवेग (Momentum) क्या है?", "mv²", "mv", "m/v", "1/2 mv²", "B", "p = m × v.", "p = mv।"),
                SeedQ("SI unit of Work and Energy is:", "कार्य और ऊर्जा का SI मात्रक क्या है?", "Newton", "Watt", "Joule", "Pascal", "C", "Joule.", "जूल (Joule)।"),
                SeedQ("Value of acceleration due to gravity g on Earth is approx:", "पृथ्वी पर गुरुत्वीय त्वरण (g) का मान लगभग होता है:", "6.8 m/s²", "9.8 m/s²", "11.2 m/s²", "12.5 m/s²", "B", "g ≈ 9.8 m/s².", "9.8 मी/से²।"),
                SeedQ("Inertia of an object depends on its:", "किसी वस्तु का जड़त्व (Inertia) किस पर निर्भर करता है?", "Velocity", "Mass", "Volume", "Shape", "B", "Mass is the measure of inertia.", "द्रव्यमान (Mass) पर।"),
                SeedQ("1 Horsepower (HP) equals how many Watts?", "1 अश्वशक्ति (Horsepower) कितने वाट के बराबर होती है?", "546 W", "646 W", "746 W", "846 W", "C", "1 HP = 746 Watts.", "746 वाट।")
            )
            "oly_c9_sec_reas" -> listOf(
                SeedQ("Find missing term: 2, 12, 36, 80, 150, ?", "लुप्त पद ज्ञात करें: 2, 12, 36, 80, 150, ?", "210", "242", "252", "260", "C", "n³ + n² pattern: 6³ + 6² = 216 + 36 = 252.", "6³ + 6² = 252।"),
                SeedQ("In a group of 50 people, 30 speak Hindi and 25 speak English. How many speak both?", "50 लोगों में 30 हिन्दी व 25 अंग्रेजी बोलते हैं। दोनों भाषाएं कितने लोग बोलते हैं?", "5", "10", "15", "20", "A", "30 + 25 - 50 = 5.", "30 + 25 - 50 = 5।"),
                SeedQ("At 4:30, find the acute angle between clock hands.", "4:30 बजे घड़ी की सुइयों के बीच न्यूनकोण कितना होगा?", "30°", "45°", "60°", "75°", "B", "|30(4) - 5.5(30)| = |120 - 165| = 45°.", "45°।"),
                SeedQ("A cube is painted red on all faces and cut into 27 small cubes. How many have 3 faces painted?", "एक घन को लाल रंगकर 27 छोटे घनों में काटा गया। 3 सतह रंगे कितने घन होंगे?", "4", "6", "8", "12", "C", "Corner cubes always = 8.", "कोने के 8 घन।"),
                SeedQ("If NORTH is coded as SOUTH, EAST as WEST, then SUN rises in:", "यदि उत्तर को दक्षिण और पूर्व को पश्चिम कहें, तो सूर्य किस दिशा में उगेगा?", "East", "West", "North", "South", "B", "East is called West.", "पश्चिम (West)।")
            )
            else -> return null
        }
        val marks = if (testId.startsWith("sim_")) 1.0 else 2.0
        return buildQuestionPack(testId, marks, items)
    }
}
