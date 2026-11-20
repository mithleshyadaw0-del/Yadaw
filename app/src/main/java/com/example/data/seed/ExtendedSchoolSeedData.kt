package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object ExtendedSchoolSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ==================== NAVODAYA CLASS 6 ====================
        makeTest(
            id = "jnv_c6_sw_math",
            title = "JNVST Class 6 - Complete Mathematics Test",
            titleHindi = "नवोदय कक्षा 6 - सम्पूर्ण गणित विषयवार टेस्ट",
            category = "Navodaya", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_sw_mat",
            title = "JNVST Class 6 - Complete Mental Ability Test",
            titleHindi = "नवोदय कक्षा 6 - सम्पूर्ण मानसिक योग्यता टेस्ट",
            category = "Navodaya", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "Reasoning / Mental Ability", totalQuestions = 5, durationMinutes = 10, marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_adv_rank",
            title = "JNVST Class 6 - Toppers Rank Booster (Advanced)",
            titleHindi = "नवोदय कक्षा 6 - एडवांस्ड रैंक बूस्टर टेस्ट",
            category = "Navodaya", examClass = "Class 6", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_pyq_2025",
            title = "JNVST Class 6 Official PYQ Paper (2025)",
            titleHindi = "नवोदय कक्षा 6 विगत वर्ष प्रश्न पत्र (2025)",
            category = "Navodaya", examClass = "Class 6", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.25
        ),
        makeTest(
            id = "jnv_c6_pyq_2024_mar",
            title = "JNVST Class 6 PYQ Marathon Live Set (2024)",
            titleHindi = "नवोदय कक्षा 6 PYQ मैराथन लाइव टेस्ट (2024)",
            category = "Navodaya", examClass = "Class 6", testType = "PYQ",
            subType = "Marathon", subject = "Mathematics", examYear = "2024",
            totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 1.25
        ),

        // ==================== NAVODAYA CLASS 9 ====================
        makeTest(
            id = "jnv_c9_sw_math",
            title = "JNVST Class 9 - Complete Mathematics Subject Test",
            titleHindi = "नवोदय कक्षा 9 - सम्पूर्ण गणित विषयवार टेस्ट",
            category = "Navodaya", examClass = "Class 9", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_sw_sci",
            title = "JNVST Class 9 - Complete Science Subject Test",
            titleHindi = "नवोदय कक्षा 9 - सम्पूर्ण विज्ञान विषयवार टेस्ट",
            category = "Navodaya", examClass = "Class 9", testType = "SUBJECT_WISE",
            subject = "Science", totalQuestions = 5, durationMinutes = 10, marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_adv_rank",
            title = "JNVST Class 9 - High Difficulty Merit Test",
            titleHindi = "नवोदय कक्षा 9 - एडवांस्ड मेरिट टेस्ट",
            category = "Navodaya", examClass = "Class 9", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.0
        ),
        makeTest(
            id = "jnv_c9_pyq_2025",
            title = "JNVST Class 9 Official PYQ Paper (2025)",
            titleHindi = "नवोदय कक्षा 9 विगत वर्ष प्रश्न पत्र (2025)",
            category = "Navodaya", examClass = "Class 9", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.0
        ),

        // ==================== SAINIK SCHOOL CLASS 6 ====================
        makeTest(
            id = "sainik_c6_sw_math",
            title = "AISSEE Class 6 - Complete Mathematics Subject Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 - सम्पूर्ण गणित विषय टेस्ट",
            category = "Sainik School", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_sw_gk",
            title = "AISSEE Class 6 - Complete GK & Defence Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 - सम्पूर्ण सामान्य ज्ञान टेस्ट",
            category = "Sainik School", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "General Knowledge", totalQuestions = 5, durationMinutes = 10, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c6_adv_merit",
            title = "AISSEE Class 6 - Sainik Merit Rank Booster",
            titleHindi = "सैनिक स्कूल कक्षा 6 - एडवांस्ड रैंक बूस्टर",
            category = "Sainik School", examClass = "Class 6", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_pyq_2025",
            title = "AISSEE Class 6 Official PYQ Paper (2025)",
            titleHindi = "सैनिक स्कूल कक्षा 6 विगत वर्ष पेपर (2025)",
            category = "Sainik School", examClass = "Class 6", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_pyq_2024_mar",
            title = "AISSEE Class 6 PYQ Marathon Live (2024)",
            titleHindi = "सैनिक स्कूल कक्षा 6 PYQ मैराथन टेस्ट (2024)",
            category = "Sainik School", examClass = "Class 6", testType = "PYQ",
            subType = "Marathon", subject = "Mathematics", examYear = "2024",
            totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 3.0
        ),

        // ==================== SAINIK SCHOOL CLASS 9 ====================
        makeTest(
            id = "sainik_c9_sw_math",
            title = "AISSEE Class 9 - Complete Mathematics Subject Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 - सम्पूर्ण गणित विषय टेस्ट",
            category = "Sainik School", examClass = "Class 9", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_adv_merit",
            title = "AISSEE Class 9 - Advanced Officer Selection Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 - एडवांस्ड सिलेक्शन टेस्ट",
            category = "Sainik School", examClass = "Class 9", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_pyq_2025",
            title = "AISSEE Class 9 Official PYQ Paper (2025)",
            titleHindi = "सैनिक स्कूल कक्षा 9 विगत वर्ष पेपर (2025)",
            category = "Sainik School", examClass = "Class 9", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 4.0
        ),

        // ==================== SIMULTALA CLASS 6 ====================
        makeTest(
            id = "sim_c6_sw_math",
            title = "Simultala Class 6 - Complete Mathematics Subject Test",
            titleHindi = "सिमुलतला कक्षा 6 - सम्पूर्ण गणित विषय टेस्ट",
            category = "Simultala", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_adv_mains",
            title = "Simultala Class 6 - Mains Level Advanced Test",
            titleHindi = "सिमुलतला कक्षा 6 - मुख्य परीक्षा स्तर एडवांस्ड टेस्ट",
            category = "Simultala", examClass = "Class 6", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.0
        ),
        makeTest(
            id = "sim_c6_pyq_2025",
            title = "Simultala Class 6 Official PYQ Paper (2025)",
            titleHindi = "सिमुलतला कक्षा 6 विगत वर्ष पेपर (2025)",
            category = "Simultala", examClass = "Class 6", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.0
        ),

        // ==================== OLYMPIAD CLASS 6 & 9 ====================
        makeTest(
            id = "oly_c6_sw_math",
            title = "Olympiad Class 6 - IMO Complete Subject Test",
            titleHindi = "ओलंपियाड कक्षा 6 - सम्पूर्ण गणित विषय टेस्ट",
            category = "Olympiad", examClass = "Class 6", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_adv_ach",
            title = "Olympiad Class 6 - Achievers Section (Advanced)",
            titleHindi = "ओलंपियाड कक्षा 6 - अचीवर्स एडवांस्ड टेस्ट",
            category = "Olympiad", examClass = "Class 6", testType = "ADVANCED",
            subType = "Advanced", subject = "Mathematics", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c6_pyq_2025",
            title = "IMO Olympiad Class 6 PYQ Set (2025)",
            titleHindi = "गणित ओलंपियाड कक्षा 6 PYQ पेपर (2025)",
            category = "Olympiad", examClass = "Class 6", testType = "PYQ",
            subType = "Previous Year", subject = "Mathematics", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_sw_math",
            title = "Olympiad Class 9 - IMO Complete Subject Test",
            titleHindi = "ओलंपियाड कक्षा 9 - सम्पूर्ण गणित विषय टेस्ट",
            category = "Olympiad", examClass = "Class 9", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_adv_ach",
            title = "Olympiad Class 9 - Achievers Section (Advanced)",
            titleHindi = "ओलंपियाड कक्षा 9 - अचीवर्स एडवांस्ड टेस्ट",
            category = "Olympiad", examClass = "Class 9", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 2.0
        ),
        makeTest(
            id = "oly_c9_pyq_2025",
            title = "IMO Olympiad Class 9 PYQ Set (2025)",
            titleHindi = "गणित ओलंपियाड कक्षा 9 PYQ पेपर (2025)",
            category = "Olympiad", examClass = "Class 9", testType = "PYQ",
            subType = "Previous Year", subject = "Mathematics", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 2.0
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "jnv_c6_sw_math", "jnv_c6_pyq_2024_mar" -> listOf(
                SeedQ("Find the smallest 5-digit number using 4, 0, 7, 2, 9:", "अंक 4, 0, 7, 2, 9 से बनने वाली सबसे छोटी 5 अंकों की संख्या है:", "20479", "24079", "02479", "20749", "A", "Leading digit cannot be 0, so 20479.", "प्रथम अंक 0 नहीं हो सकता, अतः 20479।"),
                SeedQ("A room is 15 m long and 10 m wide. Find cost of carpeting at ₹40/m²:", "15 मी × 10 मी कमरे में ₹40 प्रति वर्ग मी की दर से कालीन बिछाने का खर्च:", "₹5,000", "₹6,000", "₹7,500", "₹4,500", "B", "Area = 150 m², Cost = 150 × 40 = ₹6,000.", "क्षेत्रफल = 150, खर्च = ₹6,000।"),
                SeedQ("What is 3/5 expressed as a percentage?", "भिन्न 3/5 को प्रतिशत में व्यक्त करें:", "40%", "50%", "60%", "75%", "C", "3/5 × 100 = 60%.", "3/5 × 100 = 60%।"),
                SeedQ("Product of two numbers is 432 and HCF is 6. Find LCM:", "दो संख्याओं का गुणनफल 432 और म.स. 6 है। ल.स. ज्ञात करें:", "54", "64", "72", "84", "C", "LCM = 432 / 6 = 72.", "ल.स. = 432 / 6 = 72।"),
                SeedQ("Find simple interest on ₹1,200 at 10% for 2.5 years:", "₹1,200 पर 10% वार्षिक दर से 2.5 वर्ष का साधारण ब्याज:", "₹250", "₹300", "₹350", "₹400", "B", "(1200 × 10 × 2.5)/100 = ₹300.", "साधारण ब्याज = ₹300।")
            )
            "jnv_c6_sw_mat" -> listOf(
                SeedQ("Find next term: 4, 9, 16, 25, ?", "अगला पद ज्ञात करें: 4, 9, 16, 25, ?", "32", "35", "36", "49", "C", "Squares of 2, 3, 4, 5, 6 => 36.", "वर्ग संख्याएं: 6² = 36।"),
                SeedQ("Pen : Ink :: Car : ?", "कलम : स्याही :: कार : ?", "Road", "Petrol", "Wheel", "Driver", "B", "Pen runs with Ink; Car runs with Petrol.", "कार पेट्रोल से चलती है।"),
                SeedQ("Odd one out: Triangle, Circle, Rectangle, Sphere", "असंगत चुनें: त्रिभुज, वृत्त, आयत, गोला", "Triangle", "Circle", "Rectangle", "Sphere", "D", "Sphere is 3D; others are 2D shapes.", "गोला 3D आकृति है।"),
                SeedQ("In mirror image, letter 'E' appears inverted horizontally. Which stays same?", "दर्पण प्रतिबिंब में कौन सा अक्षर अपरिवर्तित रहता है?", "E", "F", "A", "L", "C", "A is vertically symmetric.", "अक्षर 'A' समान दिखता है।"),
                SeedQ("If 12 + 3 = 36, 15 + 4 = 60, then 11 + 5 = ?", "यदि 12+3=36, 15+4=60, तो 11+5=?", "16", "45", "55", "65", "C", "Operation is multiplication: 11 × 5 = 55.", "यहाँ गुणा हो रहा है: 11 × 5 = 55।")
            )
            "jnv_c6_adv_rank", "jnv_c6_pyq_2025" -> listOf(
                SeedQ("A bus travels 180 km in 3 hr and next 120 km in 2 hr. Average speed is:", "एक बस 3 घंटे में 180 किमी व अगले 2 घंटे में 120 किमी चलती है। औसत चाल:", "55 km/h", "60 km/h", "65 km/h", "70 km/h", "B", "Total distance 300 / Total time 5 = 60 km/h.", "कुल दूरी 300 / कुल समय 5 = 60 किमी/घंटा।"),
                SeedQ("How many bricks of 20 cm × 10 cm are needed for a floor 4 m × 3 m?", "4 मी × 3 मी फर्श के लिए 20 सेमी × 10 सेमी की कितनी ईंटें लगेंगी?", "400", "500", "600", "800", "C", "(400 × 300) / (20 × 10) = 600.", "120000 / 200 = 600 ईंटें।"),
                SeedQ("Sum of first 6 multiples of 7 is:", "7 के प्रथम 6 गुणजों का योग क्या है?", "140", "147", "154", "161", "B", "7 × (1+2+3+4+5+6) = 7 × 21 = 147.", "7 × 21 = 147।"),
                SeedQ("Which word means 'कठिनाई से मिलने वाला'?", "'कठिनाई से मिलने वाला' वाक्यांश के लिए एक शब्द है:", "सुलभ", "दुर्लभ", "अलभ्य", "सरल", "B", "दुर्लभ means rare/hard to obtain.", "दुर्लभ।"),
                SeedQ("Find missing number: 1, 1, 2, 3, 5, 8, ?", "लुप्त संख्या बताएं: 1, 1, 2, 3, 5, 8, ?", "11", "12", "13", "15", "C", "Fibonacci series: 5 + 8 = 13.", "पिछली दो संख्याओं का योग: 5 + 8 = 13।")
            )
            "jnv_c9_sw_math", "jnv_c9_adv_rank", "jnv_c9_pyq_2025" -> listOf(
                SeedQ("Find value of (0.04)⁻¹·⁵:", "(0.04)⁻¹·⁵ का मान क्या होगा?", "25", "125", "250", "625", "B", "(1/25)^(-3/2) = 25^(3/2) = 5³ = 125.", "5³ = 125।"),
                SeedQ("Area of rhombus with diagonals 16 cm and 12 cm:", "16 सेमी व 12 सेमी विकर्ण वाले समचतुर्भुज का क्षेत्रफल:", "48 cm²", "96 cm²", "112 cm²", "192 cm²", "B", "(1/2) × 16 × 12 = 96 cm².", "1/2 × 16 × 12 = 96 सेमी²।"),
                SeedQ("If x² + y² = 29 and xy = 10, find x + y (x,y > 0):", "यदि x²+y² = 29 व xy = 10 हो, तो x+y =", "6", "7", "8", "9", "B", "(x+y)² = 29 + 20 = 49 => x+y = 7.", "x+y = 7।"),
                SeedQ("A dealer gives 20% discount and still gains 20%. If CP is ₹400, MP is:", "20% छूट देकर भी 20% लाभ होता है। यदि CP ₹400 हो, तो MP:", "₹550", "₹600", "₹640", "₹720", "B", "SP = 480; MP = 480 / 0.8 = ₹600.", "अंकित मूल्य = ₹600।"),
                SeedQ("Mean of first 7 prime numbers is:", "प्रथम 7 अभाज्य संख्याओं का माध्य (Mean) है:", "7.5", "8.28", "8.5", "9.0", "B", "(2+3+5+7+11+13+17)/7 = 58/7 ≈ 8.28.", "58 / 7 ≈ 8.28।")
            )
            "jnv_c9_sw_sci" -> listOf(
                SeedQ("Which hormone regulates blood sugar level?", "रक्त शर्करा (Blood sugar) को नियंत्रित करने वाला हार्मोन है:", "Adrenaline", "Insulin", "Thyroxine", "Estrogen", "B", "Insulin secreted by pancreas.", "इंसुलिन।"),
                SeedQ("Splitting of white light into 7 colours is called:", "श्वेत प्रकाश का 7 रंगों में विभक्त होना कहलाता है:", "Reflection", "Dispersion", "Refraction", "Diffraction", "B", "Dispersion of light.", "वर्ण विक्षेपण (Dispersion)।"),
                SeedQ("Which non-metal conducts electricity?", "कौन सी अधातु विद्युत की सुचालक होती है?", "Sulphur", "Graphite", "Phosphorus", "Iodine", "B", "Graphite (allotrope of carbon).", "ग्रेफाइट।"),
                SeedQ("Unit of frequency of sound is:", "ध्वनि की आवृत्ति का मात्रक क्या है?", "Decibel", "Hertz", "Second", "Metre", "B", "Hertz (Hz).", "हर्ट्ज (Hz)।"),
                SeedQ("Microorganism used in making bread & alcohol:", "ब्रेड व अल्कोहल बनाने में प्रयुक्त सूक्ष्मजीव है:", "Amoeba", "Yeast", "Virus", "Paramecium", "B", "Yeast (fermentation).", "यीस्ट (खमीर)।")
            )
            "sainik_c6_sw_math", "sainik_c6_pyq_2024_mar", "sainik_c6_adv_merit", "sainik_c6_pyq_2025" -> listOf(
                SeedQ("Roman numeral for 490 is:", "490 का रोमन संख्यांक क्या है?", "XD", "CDXC", "CCCCXC", "LD", "B", "CD (400) + XC (90) = CDXC.", "CDXC।"),
                SeedQ("A train leaves Delhi at 8:15 AM and reaches Agra in 2 hr 50 min. Arrival time:", "एक ट्रेन सुबह 8:15 पर चलकर 2 घंटे 50 मिनट में आगरा पहुँचती है। समय:", "10:55 AM", "11:05 AM", "11:15 AM", "10:45 AM", "B", "8:15 + 2h 50m = 11:05 AM.", "11:05 AM।"),
                SeedQ("If 30% of A = 60% of B, find A : B:", "यदि A का 30% = B का 60%, तो A : B =", "1 : 2", "2 : 1", "3 : 2", "2 : 3", "B", "A/B = 60/30 = 2 : 1.", "2 : 1।"),
                SeedQ("Perimeter of equilateral triangle is 54 cm. Side length is:", "समबाहु त्रिभुज का परिमाप 54 सेमी है। भुजा की लंबाई:", "15 cm", "18 cm", "21 cm", "27 cm", "B", "54 / 3 = 18 cm.", "54 / 3 = 18 सेमी।"),
                SeedQ("Find LCM of 18, 24, 36:", "18, 24 और 36 का ल.स. (LCM) ज्ञात करें:", "48", "72", "96", "144", "B", "72 is smallest common multiple.", "72।")
            )
            "sainik_c6_sw_gk" -> listOf(
                SeedQ("Who was the first woman Prime Minister of India?", "भारत की प्रथम महिला प्रधानमंत्री कौन थीं?", "Sarojini Naidu", "Indira Gandhi", "Pratibha Patil", "Sucheta Kriplani", "B", "Indira Gandhi.", "इंदिरा गांधी।"),
                SeedQ("Which organ purifies blood in the human body?", "मानव शरीर में रक्त को शुद्ध करने वाला अंग है:", "Heart", "Kidney", "Lungs", "Liver", "B", "Kidneys filter metabolic waste from blood.", "वृक्क (Kidney)।"),
                SeedQ("Headquarters of Indian Air Force is at:", "भारतीय वायुसेना का मुख्यालय कहाँ स्थित है?", "Mumbai", "New Delhi", "Pune", "Bengaluru", "B", "Vayu Bhawan, New Delhi.", "नई दिल्ली।"),
                SeedQ("Largest ocean on Earth is:", "पृथ्वी का सबसे बड़ा महासागर कौन सा है?", "Atlantic", "Indian", "Pacific", "Arctic", "C", "Pacific Ocean.", "प्रशांत महासागर।"),
                SeedQ("Which instrument measures atmospheric pressure?", "वायुमंडलीय दाब किस यंत्र से मापा जाता है?", "Thermometer", "Barometer", "Hygrometer", "Lactometer", "B", "Barometer.", "बैरोमीटर।")
            )
            "sainik_c9_sw_math", "sainik_c9_adv_merit", "sainik_c9_pyq_2025" -> listOf(
                SeedQ("A pipe fills tank in 6 hr, another empties in 8 hr. Net time to fill:", "एक पाइप 6 घंटे में भरता है व दूसरा 8 घंटे में खाली करता है। टंकी भरने का समय:", "14 hr", "24 hr", "36 hr", "48 hr", "B", "1/6 - 1/8 = 1/24 => 24 hr.", "24 घंटे।"),
                SeedQ("If 3x + 2y = 12 and xy = 6, find 9x² + 4y²:", "यदि 3x + 2y = 12 व xy = 6 हो, तो 9x² + 4y² =", "72", "84", "108", "144", "A", "12² - 12(6) = 144 - 72 = 72.", "72।"),
                SeedQ("Diagonal of square is 10√2 cm. Area is:", "एक वर्ग का विकर्ण 10√2 सेमी है। क्षेत्रफल होगा:", "50 cm²", "100 cm²", "200 cm²", "144 cm²", "B", "Side = 10 => Area = 100 cm².", "100 सेमी²।"),
                SeedQ("Probability of getting an even number on throwing a die:", "एक पासा फेंकने पर सम संख्या आने की प्रायिकता:", "1/6", "1/3", "1/2", "2/3", "C", "3/6 = 1/2.", "3/6 = 1/2।"),
                SeedQ("Value of √(0.0169) is:", "√(0.0169) का मान क्या है?", "0.013", "0.13", "1.3", "0.0013", "B", "13² = 169 => 0.13.", "0.13।")
            )
            "sim_c6_sw_math", "sim_c6_adv_mains", "sim_c6_pyq_2025" -> listOf(
                SeedQ("Find the average of 12, 16, 20, 24, 28:", "12, 16, 20, 24, 28 का औसत ज्ञात करें:", "18", "20", "22", "24", "B", "Middle value of AP = 20.", "औसत = 20।"),
                SeedQ("A man buys a cycle for ₹1,500 and sells for ₹1,200. Loss %:", "एक व्यक्ति ₹1,500 में साइकिल खरीदकर ₹1,200 में बेचता है। हानि %:", "15%", "20%", "25%", "30%", "B", "(300/1500) × 100 = 20%.", "हानि = 20%।"),
                SeedQ("Greatest 3-digit number divisible by 15 is:", "15 से पूर्णतः विभाजित होने वाली 3 अंकों की सबसे बड़ी संख्या:", "990", "985", "975", "995", "A", "999 / 15 = 66 rem 9 => 990.", "990।"),
                SeedQ("How many minutes are in 3 hours 40 minutes?", "3 घंटे 40 मिनट में कुल कितने मिनट होते हैं?", "200", "210", "220", "240", "C", "3×60 + 40 = 220 minutes.", "220 मिनट।"),
                SeedQ("Which state does Bihar NOT share a border with?", "निम्न में से किस राज्य की सीमा बिहार से नहीं लगती?", "Jharkhand", "Uttar Pradesh", "West Bengal", "Madhya Pradesh", "D", "Madhya Pradesh does not border Bihar.", "मध्य प्रदेश की सीमा बिहार से नहीं लगती।")
            )
            "oly_c6_sw_math", "oly_c6_adv_ach", "oly_c6_pyq_2025" -> listOf(
                SeedQ("What is the units digit of 7 × 17 × 27 × 37?", "7 × 17 × 27 × 37 का इकाई अंक क्या होगा?", "1", "3", "7", "9", "A", "7⁴ ends in 1 (49 × 49 ends in 1).", "इकाई अंक 1 होगा।"),
                SeedQ("If 5 machines make 5 widgets in 5 min, how long do 50 machines take for 50 widgets?", "5 मशीनें 5 मिनट में 5 वस्तुएं बनाती हैं। 50 मशीनें 50 वस्तुएं कितने समय में बनाएंगी?", "1 min", "5 min", "10 min", "50 min", "B", "Each machine takes 5 min for 1 widget => 5 min.", "5 मिनट।"),
                SeedQ("How many lines of symmetry does a regular hexagon have?", "एक सम षट्भुज में कितनी सममित रेखाएं (Lines of symmetry) होती हैं?", "3", "4", "6", "12", "C", "6 lines of symmetry.", "6 सममित रेखाएं।"),
                SeedQ("Sum of all prime numbers less than 15 is:", "15 से छोटी सभी अभाज्य संख्याओं का योग है:", "39", "41", "43", "47", "B", "2+3+5+7+11+13 = 41.", "41।"),
                SeedQ("Perimeter of rectangle is 40 cm. If length is 12 cm, breadth is:", "आयत का परिमाप 40 सेमी व लंबाई 12 सेमी है। चौड़ाई ज्ञात करें:", "6 cm", "8 cm", "10 cm", "14 cm", "B", "40/2 - 12 = 8 cm.", "8 सेमी।")
            )
            "oly_c9_sw_math", "oly_c9_adv_ach", "oly_c9_pyq_2025" -> listOf(
                SeedQ("If α, β are roots of x² - 5x + 6 = 0, find α² + β²:", "यदि x² - 5x + 6 = 0 के मूल α, β हों, तो α² + β² =", "11", "13", "17", "25", "B", "(α+β)² - 2αβ = 25 - 12 = 13.", "25 - 12 = 13।"),
                SeedQ("Two dice are thrown. Probability of getting sum 7 is:", "दो पासे फेंके गए। अंकों का योग 7 आने की प्रायिकता:", "1/6", "1/12", "5/36", "7/36", "A", "6 favorable pairs out of 36 => 1/6.", "6/36 = 1/6।"),
                SeedQ("Number of zeros at end of 25! is:", "25! (Factorial) के अंत में कितने शून्य होंगे?", "4", "5", "6", "7", "C", "⌊25/5⌋ + ⌊25/25⌋ = 5 + 1 = 6.", "6 शून्य।"),
                SeedQ("In right triangle ABC, ∠B=90°, AB=8, BC=15. Hypotenuse AC:", "समकोण त्रिभुज ABC में ∠B=90°, AB=8, BC=15 हो, तो कर्ण AC:", "16", "17", "19", "23", "B", "√(64 + 225) = √289 = 17.", "17।"),
                SeedQ("If log₁₀(x) = 3, find x:", "यदि log₁₀(x) = 3 हो, तो x का मान होगा:", "30", "100", "300", "1000", "D", "10³ = 1000.", "1000।")
            )
            else -> return null
        }
        val marks = when {
            testId.startsWith("jnv_c6") -> 1.25
            testId.startsWith("sainik_c6_sw_gk") -> 2.0
            testId.startsWith("sainik_c6") -> 3.0
            testId.startsWith("sainik_c9") -> 4.0
            testId.startsWith("oly_") -> 2.0
            else -> 1.0
        }
        return buildQuestionPack(testId, marks, items)
    }
}
