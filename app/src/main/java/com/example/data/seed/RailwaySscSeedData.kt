package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object RailwaySscSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ============================================================
        // RAILWAY CBT (RRB NTPC / GROUP D / ALP)
        // Note: test_railway_math_01 is preserved in SeedExamData as Mock Test 01
        // ============================================================
        makeTest(
            id = "rail_fl_pyq",
            title = "RRB NTPC & Group D - Previous Year Paper (PYQ)",
            titleHindi = "रेलवे NTPC व ग्रुप D - विगत वर्ष का पेपर",
            category = "Railway",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_fl_model",
            title = "Railway CBT - Complete Model Paper 01",
            titleHindi = "रेलवे CBT - पूर्ण मॉडल पेपर 01",
            category = "Railway",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Model Paper",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_cw_math_tsd",
            title = "Time, Speed, Distance & Train Problems",
            titleHindi = "समय, चाल, दूरी एवं रेलगाड़ी आधारित प्रश्न",
            category = "Railway",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Time, Speed & Train (चाल, समय व रेलगाड़ी)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_cw_math_tw",
            title = "Time & Work, Pipes & Cisterns",
            titleHindi = "समय और कार्य एवं नल-टंकी अभ्यास",
            category = "Railway",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Time & Work (समय व कार्य)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_cw_reas_syl",
            title = "Syllogism, Blood Relation & Coding",
            titleHindi = "न्याय निगमन, रक्त संबंध एवं कोडिंग",
            category = "Railway",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Reasoning / Mental Ability",
            chapter = "Coding & Blood Relation (कोडिंग व रक्त संबंध)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_cw_sci_phy",
            title = "Railway Special General Science (Physics & Chem)",
            titleHindi = "रेलवे विशेष सामान्य विज्ञान (भौतिक व रसायन)",
            category = "Railway",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Science",
            chapter = "Physics Units & Optics (भौतिक विज्ञान)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        // Sectional Railway
        makeTest(
            id = "rail_sec_math",
            title = "Railway Mathematics Sectional Test",
            titleHindi = "रेलवे गणित सेक्शनल टेस्ट",
            category = "Railway",
            examClass = "",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_sec_reas",
            title = "Railway Reasoning Sectional Test",
            titleHindi = "रेलवे रीजनिंग सेक्शनल टेस्ट",
            category = "Railway",
            examClass = "",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_sec_sci",
            title = "Railway General Science Sectional Test",
            titleHindi = "रेलवे सामान्य विज्ञान सेक्शनल टेस्ट",
            category = "Railway",
            examClass = "",
            testType = "SECTIONAL",
            subject = "Science",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_sec_gk",
            title = "Railway General Awareness Sectional Test",
            titleHindi = "रेलवे सामान्य ज्ञान सेक्शनल टेस्ट",
            category = "Railway",
            examClass = "",
            testType = "SECTIONAL",
            subject = "General Knowledge",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33
        ),

        // ============================================================
        // SSC MATHS BOOSTER (CGL / CHSL / MTS / GD)
        // Note: test_ssc_cgl_chsl_02 is preserved in SeedExamData as Mock Test 01
        // ============================================================
        makeTest(
            id = "ssc_fl_pyq",
            title = "SSC CGL & CHSL - Previous Year Paper (PYQ)",
            titleHindi = "SSC CGL व CHSL - विगत वर्ष का प्रश्न पत्र",
            category = "SSC",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_fl_practice",
            title = "SSC Tier-1 Complete Practice Test 01",
            titleHindi = "एसएससी टियर-1 अभ्यास टेस्ट 01",
            category = "SSC",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Practice Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_cw_math_trig",
            title = "Trigonometry & Identities Booster",
            titleHindi = "त्रिकोणमिति एवं सर्वसमिकाएं (SSC Special)",
            category = "SSC",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Trigonometry (त्रिकोणमिति)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_cw_math_alg",
            title = "Algebra & Surds-Indices Tricks",
            titleHindi = "बीजगणित एवं घातांक-करणी शॉर्ट ट्रिक्स",
            category = "SSC",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Algebra & Indices (बीजगणित)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_cw_eng_idiom",
            title = "Idioms, One Word & Synonyms",
            titleHindi = "अंग्रेजी शब्दावली - Idioms व One Word",
            category = "SSC",
            examClass = "",
            testType = "CHAPTER_WISE",
            subject = "English",
            chapter = "Vocabulary & Idioms (शब्दावली)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        // Sectional SSC
        makeTest(
            id = "ssc_sec_math",
            title = "SSC Quantitative Aptitude Sectional Test",
            titleHindi = "SSC गणित (Quant) सेक्शनल टेस्ट",
            category = "SSC",
            examClass = "",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_sec_reas",
            title = "SSC General Intelligence Sectional Test",
            titleHindi = "SSC रीजनिंग सेक्शनल टेस्ट",
            category = "SSC",
            examClass = "",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_sec_eng",
            title = "SSC English Comprehension Sectional Test",
            titleHindi = "SSC अंग्रेजी सेक्शनल टेस्ट",
            category = "SSC",
            examClass = "",
            testType = "SECTIONAL",
            subject = "English",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_sec_gk",
            title = "SSC General Awareness Sectional Test",
            titleHindi = "SSC सामान्य अध्ययन सेक्शनल टेस्ट",
            category = "SSC",
            examClass = "",
            testType = "SECTIONAL",
            subject = "General Knowledge",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "rail_fl_pyq", "rail_fl_model", "rail_sec_math" -> listOf(
                SeedQ("A train 240 m long crosses a platform of 160 m in 20 s. Find speed in km/h.", "240 मी लंबी ट्रेन 160 मी लंबे प्लेटफॉर्म को 20 सेकंड में पार करती है। चाल किमी/घंटा में बताएं:", "60 km/h", "72 km/h", "80 km/h", "90 km/h", "B", "(240+160)/20 = 20 m/s = 20 × 18/5 = 72 km/h.", "400/20 = 20 मी/से = 72 किमी/घंटा।"),
                SeedQ("SI unit of Electric Resistance is:", "विद्युत प्रतिरोध (Resistance) का SI मात्रक क्या है?", "Volt", "Ampere", "Ohm", "Watt", "C", "Ohm (Ω).", "ओम (Ohm)।"),
                SeedQ("Who was the first Railway Minister of independent India?", "स्वतंत्र भारत के प्रथम रेल मंत्री कौन थे?", "Lal Bahadur Shastri", "John Mathai", "Asaf Ali", "Jagjivan Ram", "B", "John Mathai.", "जॉन मथाई।"),
                SeedQ("If x = 7 - 4√3, find x + 1/x.", "यदि x = 7 - 4√3 हो, तो x + 1/x का मान बताएं:", "14", "8√3", "7", "12", "A", "1/x = 7 + 4√3 => x + 1/x = 14.", "7 + 7 = 14।"),
                SeedQ("Headquarters of East Central Railway (ECR) is at:", "पूर्व मध्य रेलवे (ECR) का मुख्यालय कहाँ स्थित है?", "Patna", "Hajipur", "Gorakhpur", "Kolkata", "B", "Hajipur (Bihar).", "हाजीपुर।")
            )
            "rail_cw_math_tsd" -> listOf(
                SeedQ("Convert 90 km/h into m/s.", "90 किमी/घंटा को मीटर/सेकंड में बदलें।", "20 m/s", "25 m/s", "30 m/s", "35 m/s", "B", "90 × 5/18 = 25 m/s.", "90 × 5/18 = 25 मी/से।"),
                SeedQ("A boat speed in still water is 12 km/h and stream speed is 3 km/h. Downstream speed is:", "शांत जल में नाव की चाल 12 किमी/घं तथा धारा की 3 किमी/घं है। अनुप्रवाह चाल बताएं:", "9 km/h", "12 km/h", "15 km/h", "18 km/h", "C", "12 + 3 = 15 km/h.", "12 + 3 = 15 किमी/घंटा।"),
                SeedQ("Two trains 120 m & 130 m run in opposite directions at 40 & 50 km/h. Time to cross:", "120 मी और 130 मी दो ट्रेनें विपरीत दिशा में 40 व 50 किमी/घं से चलती हैं। पार करने का समय:", "8 sec", "10 sec", "12 sec", "15 sec", "B", "250 / (90 × 5/18) = 250 / 25 = 10 s.", "250 / 25 = 10 सेकंड।"),
                SeedQ("Walking at 3/4 of usual speed, a man is 10 mins late. Find usual time.", "सामान्य चाल की 3/4 चाल से चलने पर 10 मिनट की देरी होती है। सामान्य समय:", "20 min", "30 min", "40 min", "45 min", "B", "1/3 of T = 10 => T = 30 min.", "30 मिनट।"),
                SeedQ("Average speed for equal distances at 40 km/h and 60 km/h is:", "समान दूरी को 40 व 60 किमी/घं से तय करने पर औसत चाल होगी:", "48 km/h", "50 km/h", "52 km/h", "54 km/h", "A", "2xy/(x+y) = 4800/100 = 48 km/h.", "48 किमी/घंटा।")
            )
            "rail_cw_math_tw" -> listOf(
                SeedQ("A completes work in 12 days, B in 24 days. Together they finish in:", "A किसी कार्य को 12 दिन तथा B 24 दिन में करता है। दोनों मिलकर पूरा करेंगे:", "6 days", "8 days", "9 days", "10 days", "B", "(12×24)/(12+24) = 8 days.", "8 दिन।"),
                SeedQ("Pipe A fills in 10 hr, Pipe B empties in 15 hr. Together tank fills in:", "नल A 10 घंटे में भरता है व B 15 घंटे में खाली करता है। दोनों खुले होने पर समय:", "20 hr", "25 hr", "30 hr", "40 hr", "C", "1/10 - 1/15 = 1/30 => 30 hours.", "30 घंटे।"),
                SeedQ("15 men finish a job in 16 days. How many men needed in 12 days?", "15 व्यक्ति एक काम 16 दिन में करते हैं। 12 दिन में पूरा करने को कितने व्यक्ति चाहिए?", "18", "20", "22", "24", "B", "15 × 16 / 12 = 20 men.", "20 व्यक्ति।"),
                SeedQ("A is twice as efficient as B. Together they take 14 days. A alone takes:", "A की कार्यक्षमता B से दोगुनी है। दोनों 14 दिन में करते हैं, तो A अकेले करेगा:", "18 days", "21 days", "28 days", "42 days", "B", "Total work = 3×14 = 42. A alone = 42/2 = 21 days.", "21 दिन।"),
                SeedQ("A, B, C work for total wage ₹900 with ratio 2:3:4. Share of C is:", "A, B, C को 2:3:4 अनुपात में ₹900 मजदूरी मिली। C का हिस्सा क्या है?", "₹200", "₹300", "₹400", "₹500", "C", "(4/9) × 900 = ₹400.", "₹400।")
            )
            "rail_cw_reas_syl", "rail_sec_reas" -> listOf(
                SeedQ("If BOOK = 43 (2+15+15+11), then PEN =", "यदि BOOK = 43 हो, तो PEN =", "32", "35", "38", "40", "B", "P(16)+E(5)+N(14) = 35.", "16+5+14 = 35।"),
                SeedQ("Pointing to a boy, Sita said 'He is the son of my mother's only brother.' Relation:", "लड़के की ओर इशारा कर सीता बोली 'वह मेरी माँ के इकलौते भाई का पुत्र है।' रिश्ता:", "Brother", "Cousin", "Uncle", "Nephew", "B", "Mother's brother is maternal uncle; his son is Cousin.", "ममेरा भाई (Cousin)।"),
                SeedQ("All cats are dogs. Some dogs are lions. Conclusion: Some lions are dogs.", "सभी बिल्लियाँ कुत्ते हैं। कुछ कुत्ते शेर हैं। निष्कर्ष: कुछ शेर कुत्ते हैं।", "Follows", "Does not follow", "Either or", "Cannot say", "A", "Converse of Some dogs are lions.", "निष्कर्ष अनुसरण करता है।"),
                SeedQ("Complete series: 7, 14, 28, 56, ?", "श्रृंखला पूरी करें: 7, 14, 28, 56, ?", "98", "104", "112", "124", "C", "56 × 2 = 112.", "56 × 2 = 112।"),
                SeedQ("Find odd one out: 27, 64, 125, 144", "असंगत चुनिए: 27, 64, 125, 144", "27", "64", "125", "144", "D", "27, 64, 125 are cubes; 144 is 12².", "144 घन नहीं है।")
            )
            "rail_cw_sci_phy", "rail_sec_sci" -> listOf(
                SeedQ("SI unit of Power of a Lens is:", "लेंस की क्षमता का SI मात्रक क्या है?", "Watt", "Dioptre", "Metre", "Joule", "B", "Dioptre (D).", "डायोप्टर।"),
                SeedQ("pH value of pure water is:", "शुद्ध जल का pH मान कितना होता है?", "5", "7", "9", "14", "B", "Pure water is neutral (pH 7).", "7 (उदासीन)।"),
                SeedQ("Which metal is stored in kerosene oil?", "किस धातु को मिट्टी के तेल (Kerosene) में रखा जाता है?", "Iron", "Sodium", "Magnesium", "Aluminium", "B", "Sodium reacts vigorously with air/water.", "सोडियम।"),
                SeedQ("Myopia (Near-sightedness) is corrected by:", "निकट दृष्टि दोष (Myopia) के निवारण हेतु किस लेंस का उपयोग होता है?", "Convex lens", "Concave lens", "Cylindrical", "Plane glass", "B", "Concave lens (अवतल लेंस).", "अवतल लेंस।"),
                SeedQ("Chemical formula of Baking Soda is:", "बेकिंग सोडा (खाने का सोडा) का रासायनिक सूत्र है:", "Na₂CO₃", "NaHCO₃", "NaCl", "CaCO₃", "B", "Sodium bicarbonate NaHCO₃.", "NaHCO₃।")
            )
            "rail_sec_gk" -> listOf(
                SeedQ("Minimum age to become Prime Minister of India is:", "भारत का प्रधानमंत्री बनने के लिए न्यूनतम आयु कितनी है?", "18 years", "21 years", "25 years", "30 years", "C", "25 years for Lok Sabha MP.", "25 वर्ष।"),
                SeedQ("Longest river of Peninsular India is:", "प्रायद्वीपीय भारत की सबसे लंबी नदी कौन सी है?", "Krishna", "Kaveri", "Godavari", "Narmada", "C", "Godavari (Dakshin Ganga).", "गोदावरी।"),
                SeedQ("Kaziranga National Park is famous for:", "काजीरंगा राष्ट्रीय उद्यान किसके लिए प्रसिद्ध है?", "Lion", "Tiger", "One-horned Rhino", "Elephant", "C", "One-horned Rhinoceros in Assam.", "एक सींग वाला गैंडा।"),
                SeedQ("Article 17 of Indian Constitution abolishes:", "भारतीय संविधान का अनुच्छेद 17 किसे समाप्त करता है?", "Child labour", "Untouchability", "Titles", "Bonded labour", "B", "Abolition of Untouchability.", "अस्पृश्यता का अंत।"),
                SeedQ("Capital of Sikkim is:", "सिक्किम की राजधानी क्या है?", "Imphal", "Itanagar", "Gangtok", "Aizawl", "C", "Gangtok.", "गंगटोक।")
            )
            // ---------------- SSC ----------------
            "ssc_fl_pyq", "ssc_fl_practice", "ssc_sec_math" -> listOf(
                SeedQ("If sec θ + tan θ = 3, find sec θ - tan θ.", "यदि sec θ + tan θ = 3 हो, तो sec θ - tan θ का मान क्या होगा?", "1/2", "1/3", "2/3", "3", "B", "sec²θ - tan²θ = 1 => difference = 1/3.", "1/3।"),
                SeedQ("Successive discounts of 20% and 10% equal single discount of:", "20% और 10% की दो क्रमिक छूट किस एकल छूट के बराबर है?", "25%", "28%", "30%", "32%", "B", "20 + 10 - (200/100) = 28%.", "28%।"),
                SeedQ("If x + 1/x = 5, find x³ + 1/x³.", "यदि x + 1/x = 5 हो, तो x³ + 1/x³ =", "100", "110", "125", "140", "B", "5³ - 3(5) = 125 - 15 = 110.", "110।"),
                SeedQ("Ratio of areas of two similar triangles with sides 3:5 is:", "3:5 भुजा वाले दो समरूप त्रिभुजों के क्षेत्रफल का अनुपात है:", "3 : 5", "9 : 25", "6 : 10", "27 : 125", "B", "(3/5)² = 9 : 25.", "9 : 25।"),
                SeedQ("Antonym of 'Benevolent' is:", "'Benevolent' (परोपकारी) का विलोम है:", "Kind", "Malevolent", "Generous", "Helpful", "B", "Malevolent means wishing harm.", "Malevolent।")
            )
            "ssc_cw_math_trig" -> listOf(
                SeedQ("Value of sin² 30° + cos² 30° is:", "sin² 30° + cos² 30° का मान क्या है?", "0", "1/2", "1", "√3/2", "C", "sin²θ + cos²θ = 1.", "सदैव 1 होता है।"),
                SeedQ("If tan A = 1, value of 2 sin A cos A is:", "यदि tan A = 1 हो, तो 2 sin A cos A का मान:", "1/2", "1", "√2", "2", "B", "A = 45°, sin 90° = 1.", "1।"),
                SeedQ("Maximum value of 3 sin θ + 4 cos θ is:", "3 sin θ + 4 cos θ का अधिकतम मान क्या होगा?", "4", "5", "7", "12", "B", "√(3² + 4²) = 5.", "√(3²+4²) = 5।"),
                SeedQ("tan 1° · tan 2° · ... · tan 89° equals:", "tan 1° · tan 2° · ... · tan 89° का मान:", "0", "1", "∞", "1/2", "B", "Complementary pairs tan θ · tan(90-θ) = 1.", "1।"),
                SeedQ("If sin θ = cos θ (acute angle), θ =", "यदि sin θ = cos θ (न्यूनकोण), तो θ =", "30°", "45°", "60°", "90°", "B", "45°.", "45°।")
            )
            "ssc_cw_math_alg" -> listOf(
                SeedQ("If x + y + z = 9 and xy + yz + zx = 23, find x² + y² + z².", "यदि x+y+z = 9 व xy+yz+zx = 23 हो, तो x²+y²+z² =", "35", "45", "55", "81", "A", "9² - 2(23) = 81 - 46 = 35.", "81 - 46 = 35।"),
                SeedQ("If a² + b² = 25 and ab = 12, find (a - b).", "यदि a²+b² = 25 व ab = 12 हो, तो (a - b) =", "0", "1", "2", "3", "B", "(a-b)² = 25 - 24 = 1 => a-b = 1.", "1।"),
                SeedQ("Value of √(12 + √(12 + √(12 + ...∞))) is:", "√(12 + √(12 + √(12 + ...∞))) का मान क्या है?", "3", "4", "6", "12", "B", "Factors 3 × 4 (positive sign -> larger 4).", "4।"),
                SeedQ("If x = 3 + 2√2, find √x - 1/√x.", "यदि x = 3 + 2√2 हो, तो √x - 1/√x =", "1", "2", "2√2", "3", "B", "√x = √2+1, 1/√x = √2-1 => diff = 2.", "2।"),
                SeedQ("Unit digit of (237)^43 is:", "(237)^43 का इकाई अंक (Unit digit) क्या है?", "1", "3", "7", "9", "B", "43 mod 4 = 3 => 7³ = 343 => 3.", "3।")
            )
            "ssc_cw_eng_idiom", "ssc_sec_eng" -> listOf(
                SeedQ("Meaning of idiom 'Break a leg':", "'Break a leg' मुहावरे का अर्थ है:", "Get injured", "Good luck", "Run fast", "Give up", "B", "It means 'Wish you good luck'.", "शुभकामनाएं देना (Good luck)।"),
                SeedQ("One word for 'A study of birds':", "'पक्षी विज्ञान' के लिए अंग्रेजी में एक शब्द:", "Botany", "Ornithology", "Zoology", "Entomology", "B", "Ornithology.", "Ornithology।"),
                SeedQ("Synonym of 'Ephemeral':", "'Ephemeral' (क्षणिक) का समानार्थी:", "Permanent", "Short-lived", "Eternal", "Strong", "B", "Short-lived / transient.", "Short-lived।"),
                SeedQ("Meaning of 'Once in a blue moon':", "'Once in a blue moon' का अर्थ:", "Daily", "Very rarely", "Monthly", "Always", "B", "Very rarely.", "बहुत कम / कभी-कभार।"),
                SeedQ("Spot correct preposition: She is fond ___ music.", "सही Preposition चुनें: She is fond ___ music.", "of", "for", "with", "in", "A", "fond of.", "fond of।")
            )
            "ssc_sec_reas" -> listOf(
                SeedQ("Find missing: 6, 13, 27, 55, ?", "लुप्त संख्या ज्ञात करें: 6, 13, 27, 55, ?", "109", "110", "111", "112", "C", "×2 + 1 => 55×2 + 1 = 111.", "111।"),
                SeedQ("12 : 144 :: 15 : ?", "12 : 144 :: 15 : ?", "200", "225", "250", "180", "B", "15² = 225.", "225।"),
                SeedQ("How many diagonals are in a pentagon?", "एक पंचभुज (Pentagon) में कितने विकर्ण होते हैं?", "4", "5", "6", "7", "B", "n(n-3)/2 = 5(2)/2 = 5.", "5।"),
                SeedQ("Mirror image of clock showing 8:20 will show:", "8:20 बजे का दर्पण प्रतिबिंब समय क्या होगा?", "3:40", "4:40", "3:20", "4:20", "A", "11:60 - 8:20 = 3:40.", "3:40।"),
                SeedQ("Odd one out: Rupee, Dollar, Yen, Tokyo", "असंगत चुनें: रुपया, डॉलर, येन, टोक्यो", "Rupee", "Dollar", "Yen", "Tokyo", "D", "Tokyo is a city; others are currencies.", "टोक्यो शहर है।")
            )
            "ssc_sec_gk" -> listOf(
                SeedQ("Who wrote 'Arthashastra'?", "'अर्थशास्त्र' पुस्तक के लेखक कौन हैं?", "Kalidasa", "Kautilya (Chanakya)", "Tulsidas", "Panini", "B", "Chanakya (Kautilya).", "चाणक्य (कौटिल्य)।"),
                SeedQ("Which gland is called Master Gland?", "किस ग्रंथि को 'मास्टर ग्रंथि' कहा जाता है?", "Thyroid", "Pituitary", "Adrenal", "Pancreas", "B", "Pituitary gland.", "पीयूष (Pituitary) ग्रंथि।"),
                SeedQ("GST in India was implemented on:", "भारत में GST कब लागू किया गया था?", "1 April 2017", "1 July 2017", "8 Nov 2016", "1 Jan 2018", "B", "1 July 2017.", "1 जुलाई 2017।"),
                SeedQ("Article 32 deals with:", "संविधान का अनुच्छेद 32 किससे संबंधित है?", "Directive Principles", "Constitutional Remedies", "Citizenship", "Emergency", "B", "Right to Constitutional Remedies.", "संवैधानिक उपचारों का अधिकार।"),
                SeedQ("Deepest ocean trench in the world is:", "विश्व की सबसे गहरी समुद्री खाई कौन सी है?", "Java Trench", "Mariana Trench", "Tonga Trench", "Puerto Rico Trench", "B", "Mariana Trench (Pacific Ocean).", "मेरियाना गर्त।")
            )
            else -> return null
        }
        val marks = if (testId.startsWith("rail_")) 1.0 else 2.0
        return buildQuestionPack(testId, marks, items)
    }
}
