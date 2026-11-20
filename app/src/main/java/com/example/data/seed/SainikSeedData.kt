package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object SainikSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ---------------- CLASS 6: FULL LENGTH ----------------
        makeTest(
            id = "sainik_c6_fl_mock",
            title = "AISSEE Class 6 - Complete Mock Test 01",
            titleHindi = "सैनिक स्कूल कक्षा 6 - पूर्ण मॉक टेस्ट 01",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_fl_pyq",
            title = "AISSEE Class 6 - Previous Year Paper (PYQ)",
            titleHindi = "सैनिक स्कूल कक्षा 6 - विगत वर्ष का पेपर",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_fl_model",
            title = "AISSEE Class 6 - Model Paper 01",
            titleHindi = "सैनिक स्कूल कक्षा 6 - मॉडल पेपर 01",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Model Paper",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 3.0
        ),

        // ---------------- CLASS 6: CHAPTER WISE ----------------
        makeTest(
            id = "sainik_c6_cw_math_unit",
            title = "Unitary Method, Average & Ratio",
            titleHindi = "ऐकिक नियम, औसत एवं अनुपात",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Unitary Method & Average (ऐकिक नियम व औसत)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_cw_math_geom",
            title = "Angles, Triangles & Perimeter",
            titleHindi = "कोण, त्रिभुज एवं परिमाप",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Angles & Geometry (कोण व ज्यामिति)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_cw_int_dir",
            title = "Number Series & Direction Sense",
            titleHindi = "संख्या श्रृंखला एवं दिशा ज्ञान",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "Reasoning / Mental Ability",
            chapter = "Series & Directions (श्रृंखला व दिशा)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c6_cw_gk_def",
            title = "Indian Defence, Symbols & Solar System",
            titleHindi = "भारतीय रक्षा बल, राष्ट्रीय प्रतीक व सौरमंडल",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "General Knowledge",
            chapter = "Defence & National Symbols (रक्षा व प्रतीक)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c6_cw_eng_pos",
            title = "Articles, Prepositions & Parts of Speech",
            titleHindi = "अंग्रेजी व्याकरण - Articles व Prepositions",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "CHAPTER_WISE",
            subject = "English",
            chapter = "Articles & Prepositions",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),

        // ---------------- CLASS 6: SECTIONAL ----------------
        makeTest(
            id = "sainik_c6_sec_math",
            title = "Class 6 Mathematics Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 गणित सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 3.0
        ),
        makeTest(
            id = "sainik_c6_sec_int",
            title = "Class 6 Intelligence Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 बुद्धिमत्ता सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c6_sec_gk",
            title = "Class 6 General Knowledge Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 सामान्य ज्ञान सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "General Knowledge",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c6_sec_eng",
            title = "Class 6 English Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 6 अंग्रेजी सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 6",
            testType = "SECTIONAL",
            subject = "English",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),

        // ---------------- CLASS 9: FULL LENGTH ----------------
        makeTest(
            id = "sainik_c9_fl_mock",
            title = "AISSEE Class 9 - Complete Mock Test 01",
            titleHindi = "सैनिक स्कूल कक्षा 9 - पूर्ण मॉक टेस्ट 01",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_fl_pyq",
            title = "AISSEE Class 9 - Previous Year Paper (PYQ)",
            titleHindi = "सैनिक स्कूल कक्षा 9 - पिछले वर्ष का पेपर",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Previous Year",
            subject = "All Subjects",
            examYear = "2024",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_fl_practice",
            title = "AISSEE Class 9 - Practice Test 01",
            titleHindi = "सैनिक स्कूल कक्षा 9 - अभ्यास टेस्ट 01",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "FULL_LENGTH",
            subType = "Practice Test",
            subject = "All Subjects",
            totalQuestions = 5,
            durationMinutes = 15,
            marksPerQuestion = 4.0
        ),

        // ---------------- CLASS 9: CHAPTER WISE ----------------
        makeTest(
            id = "sainik_c9_cw_math_ci",
            title = "Compound Interest & Discount",
            titleHindi = "चक्रवृद्धि ब्याज एवं बट्टा (Class 9)",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "Compound Interest & Discount (चक्रवृद्धि ब्याज)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_cw_math_3d",
            title = "Surface Area & Volume (3D Mensuration)",
            titleHindi = "पृष्ठीय क्षेत्रफल व आयतन (Class 9)",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Mathematics",
            chapter = "3D Mensuration (क्षेत्रमिति)",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_cw_sci_chem",
            title = "Metals, Non-Metals & Combustion",
            titleHindi = "धातु, अधातु एवं दहन (Class 9)",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "Science",
            chapter = "Metals, Non-Metals & Cell (धातु व कोशिका)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c9_cw_sst_hist",
            title = "Modern History & Indian Constitution",
            titleHindi = "आधुनिक इतिहास व भारतीय संविधान (Class 9)",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "CHAPTER_WISE",
            subject = "General Knowledge",
            chapter = "History & Constitution (इतिहास व संविधान)",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),

        // ---------------- CLASS 9: SECTIONAL ----------------
        makeTest(
            id = "sainik_c9_sec_math",
            title = "Class 9 Mathematics Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 गणित सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Mathematics",
            totalQuestions = 5,
            durationMinutes = 12,
            marksPerQuestion = 4.0
        ),
        makeTest(
            id = "sainik_c9_sec_int",
            title = "Class 9 Intelligence Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 तर्कशक्ति सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Reasoning / Mental Ability",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c9_sec_sci",
            title = "Class 9 General Science Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 विज्ञान सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "Science",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        ),
        makeTest(
            id = "sainik_c9_sec_eng",
            title = "Class 9 English Sectional Test",
            titleHindi = "सैनिक स्कूल कक्षा 9 अंग्रेजी सेक्शनल टेस्ट",
            category = "Sainik School",
            examClass = "Class 9",
            testType = "SECTIONAL",
            subject = "English",
            totalQuestions = 5,
            durationMinutes = 10,
            marksPerQuestion = 2.0
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "sainik_c6_fl_mock", "sainik_c6_fl_pyq", "sainik_c6_fl_model" -> listOf(
                SeedQ("Sum of all angles of a triangle is:", "त्रिभुज के सभी अंतःकोणों का योग कितना होता है?", "90°", "180°", "270°", "360°", "B", "Sum of angles in triangle = 180°.", "त्रिभुज के तीनों कोणों का योग 180° होता है।"),
                SeedQ("Who is known as the Father of the Indian Constitution?", "भारतीय संविधान के जनक के रूप में किसे जाना जाता है?", "Mahatma Gandhi", "Dr. B.R. Ambedkar", "Dr. Rajendra Prasad", "Sardar Patel", "B", "Dr. B.R. Ambedkar.", "डॉ. भीमराव अंबेडकर।"),
                SeedQ("Find missing number: 5, 10, 20, 40, ?", "लुप्त संख्या ज्ञात करें: 5, 10, 20, 40, ?", "60", "70", "80", "100", "C", "Each term is doubled: 40 × 2 = 80.", "प्रत्येक पद दोगुना हो रहा है: 40 × 2 = 80।"),
                SeedQ("Choose correct article: He is ___ honest officer.", "सही article चुनें: He is ___ honest officer.", "a", "an", "the", "none", "B", "'honest' begins with vowel sound 'o' => an.", "'honest' में h मूक है अतः an आएगा।"),
                SeedQ("If 12 books cost ₹480, find cost of 7 books.", "यदि 12 किताबों का मूल्य ₹480 है, तो 7 किताबों का मूल्य है:", "₹240", "₹280", "₹300", "₹320", "B", "1 book = 480/12 = ₹40. 7 books = ₹280.", "1 किताब = ₹40, 7 किताबें = ₹280।")
            )
            "sainik_c6_cw_math_unit", "sainik_c6_sec_math" -> listOf(
                SeedQ("Average of 10, 20, 30, 40, 50 is:", "10, 20, 30, 40, 50 का औसत ज्ञात करें:", "25", "30", "35", "40", "B", "150 / 5 = 30.", "150 / 5 = 30।"),
                SeedQ("Ratio of 50 cm to 2 meters is:", "50 सेमी का 2 मीटर से अनुपात क्या होगा?", "1 : 2", "1 : 4", "2 : 1", "1 : 5", "B", "2 m = 200 cm => 50 : 200 = 1 : 4.", "50 : 200 = 1 : 4।"),
                SeedQ("If 8 workers build a wall in 6 days, 12 workers will take:", "8 मजदूर एक दीवार 6 दिन में बनाते हैं, तो 12 मजदूर कितने दिन में बनाएंगे?", "3 days", "4 days", "5 days", "9 days", "B", "8 × 6 = 12 × D => D = 4 days.", "(8 × 6)/12 = 4 दिन।"),
                SeedQ("A car travels 240 km in 4 hours. Find its speed.", "एक कार 4 घंटे में 240 किमी चलती है। चाल बताएं।", "50 km/h", "60 km/h", "70 km/h", "80 km/h", "B", "240 / 4 = 60 km/h.", "चाल = 240/4 = 60 किमी/घंटा।"),
                SeedQ("15% of 200 is:", "200 का 15% कितना होता है?", "20", "25", "30", "35", "C", "(15/100) × 200 = 30.", "200 × 15/100 = 30।")
            )
            "sainik_c6_cw_math_geom" -> listOf(
                SeedQ("An angle measuring 90° is called:", "90° के कोण को क्या कहते हैं?", "Acute angle", "Right angle", "Obtuse angle", "Straight angle", "B", "90° is a Right angle (समकोण).", "90° को समकोण कहते हैं।"),
                SeedQ("Supplement of 110° is:", "110° का संपूरक कोण (Supplementary) क्या होगा?", "70°", "80°", "90°", "20°", "A", "180° - 110° = 70°.", "180° - 110° = 70°।"),
                SeedQ("Complement of 35° is:", "35° का पूरक कोण (Complementary) क्या होगा?", "45°", "55°", "65°", "145°", "B", "90° - 35° = 55°.", "90° - 35° = 55°।"),
                SeedQ("Area of rectangle with L = 14 cm, B = 6 cm is:", "14 सेमी लंबाई व 6 सेमी चौड़ाई वाले आयत का क्षेत्रफल है:", "40 cm²", "72 cm²", "84 cm²", "96 cm²", "C", "14 × 6 = 84 cm².", "14 × 6 = 84 सेमी²।"),
                SeedQ("Diameter of a circle with radius 9 cm is:", "9 सेमी त्रिज्या वाले वृत्त का व्यास कितना होगा?", "4.5 cm", "9 cm", "18 cm", "27 cm", "C", "Diameter = 2 × r = 18 cm.", "व्यास = 2 × 9 = 18 सेमी।")
            )
            "sainik_c6_cw_int_dir", "sainik_c6_sec_int" -> listOf(
                SeedQ("Ravi walks 5 km North, then turns right and walks 3 km. Which direction is he facing?", "रवि 5 किमी उत्तर चलता है, फिर दाएँ मुड़कर 3 किमी चलता है। अब उसका मुख किस दिशा में है?", "North", "South", "East", "West", "C", "Right from North is East.", "उत्तर से दाएँ मुड़ने पर पूर्व दिशा होती है।"),
                SeedQ("Complete series: 3, 9, 27, 81, ?", "श्रृंखला पूरी करें: 3, 9, 27, 81, ?", "162", "243", "210", "324", "B", "Multiply by 3: 81 × 3 = 243.", "81 × 3 = 243।"),
                SeedQ("If CAT = 24 (3+1+20), then DOG =", "यदि CAT = 24 हो, तो DOG =", "24", "26", "28", "30", "B", "D(4)+O(15)+G(7) = 26.", "4 + 15 + 7 = 26।"),
                SeedQ("Odd one out: Square, Triangle, Circle, Cube", "असंगत चुनें: वर्ग, त्रिभुज, वृत्त, घन", "Square", "Triangle", "Circle", "Cube", "D", "Cube is 3D; others are 2D shapes.", "घन 3D आकृति है, बाकी 2D हैं।"),
                SeedQ("Pen : Write :: Knife : ?", "कलम : लिखना :: चाकू : ?", "Draw", "Cut", "Paint", "Read", "B", "Knife is used to Cut.", "चाकू का उपयोग काटने के लिए होता है।")
            )
            "sainik_c6_cw_gk_def", "sainik_c6_sec_gk" -> listOf(
                SeedQ("Which is the highest gallantry award in India?", "भारत का सर्वोच्च वीरता पुरस्कार कौन सा है?", "Ashoka Chakra", "Param Vir Chakra", "Vir Chakra", "Kirti Chakra", "B", "Param Vir Chakra is the highest wartime gallantry award.", "परमवीर चक्र सर्वोच्च वीरता पुरस्कार है।"),
                SeedQ("Which planet is known as the Red Planet?", "किस ग्रह को 'लाल ग्रह' कहा जाता है?", "Venus", "Mars", "Jupiter", "Saturn", "B", "Mars is called the Red Planet.", "मंगल (Mars) को लाल ग्रह कहते हैं।"),
                SeedQ("When is Indian Navy Day celebrated?", "भारतीय नौसेना दिवस कब मनाया जाता है?", "15 January", "8 October", "4 December", "26 January", "C", "Navy Day is on 4 December.", "4 दिसंबर को नौसेना दिवस मनाया जाता है।"),
                SeedQ("Which gas is essential for breathing?", "श्वसन के लिए कौन सी गैस आवश्यक है?", "Nitrogen", "Carbon dioxide", "Oxygen", "Hydrogen", "C", "Oxygen is essential for respiration.", "ऑक्सीजन आवश्यक है।"),
                SeedQ("National Aquatic Animal of India is:", "भारत का राष्ट्रीय जलीय जीव कौन सा है?", "Blue Whale", "Ganges River Dolphin", "Shark", "Turtle", "B", "Ganges River Dolphin.", "गंगा डॉल्फिन।")
            )
            "sainik_c6_cw_eng_pos", "sainik_c6_sec_eng" -> listOf(
                SeedQ("Plural form of 'Child' is:", "'Child' का बहुवचन (Plural) क्या है?", "Childs", "Childes", "Children", "Childrens", "C", "Children is irregular plural of Child.", "Children सही बहुवचन है।"),
                SeedQ("Fill in the blank: The book is ___ the table.", "रिक्त स्थान भरें: The book is ___ the table.", "on", "in", "into", "at", "A", "Touching surface => on.", "मेज के ऊपर => on."),
                SeedQ("Identify the Adjective: 'She wears a red dress.'", "विशेषण (Adjective) चुनें: 'She wears a red dress.'", "She", "wears", "red", "dress", "C", "'red' describes the noun dress.", "'red' विशेषण है।"),
                SeedQ("Opposite of 'Victory' is:", "'Victory' का विलोम शब्द क्या है?", "Win", "Defeat", "Success", "Glory", "B", "Defeat is opposite of Victory.", "Victory (जीत) का विलोम Defeat (हार) है।"),
                SeedQ("Past tense of 'Teach' is:", "'Teach' का भूतकाल (Past tense) है:", "Teached", "Taught", "Thought", "Teaching", "B", "Teach -> Taught.", "Teach का past tense Taught है।")
            )
            // ---------------- CLASS 9 ----------------
            "sainik_c9_fl_mock", "sainik_c9_fl_pyq", "sainik_c9_fl_practice" -> listOf(
                SeedQ("Find compound interest on ₹10,000 at 10% p.a. for 2 years.", "₹10,000 पर 10% वार्षिक दर से 2 वर्ष का चक्रवृद्धि ब्याज ज्ञात करें।", "₹2,000", "₹2,100", "₹2,200", "₹2,400", "B", "10000*(1.1)² - 10000 = ₹2,100.", "चक्रवृद्धि ब्याज = ₹2,100।"),
                SeedQ("The battle of Plassey was fought in:", "प्लासी का युद्ध किस वर्ष लड़ा गया था?", "1757", "1764", "1857", "1947", "A", "Battle of Plassey took place in 1757.", "1757 में लड़ा गया था।"),
                SeedQ("Powerhouse of the cell is:", "कोशिका का ऊर्जा गृह (Powerhouse) किसे कहते हैं?", "Nucleus", "Mitochondria", "Ribosome", "Golgi body", "B", "Mitochondria generates ATP.", "माइटोकॉन्ड्रिया।"),
                SeedQ("Choose indirect speech: He said, 'I am happy.'", "Indirect Speech चुनें: He said, 'I am happy.'", "He said that he is happy.", "He said that he was happy.", "He told he was happy.", "He said I was happy.", "B", "Simple present changes to simple past => was happy.", "He said that he was happy."),
                SeedQ("If diagonal of cube is 4√3 cm, its volume is:", "यदि घन का विकर्ण 4√3 सेमी है, तो आयतन कितना होगा?", "16 cm³", "32 cm³", "64 cm³", "48 cm³", "C", "Side = 4 cm, Volume = 4³ = 64 cm³.", "भुजा = 4, आयतन = 64 सेमी³।")
            )
            "sainik_c9_cw_math_ci", "sainik_c9_sec_math" -> listOf(
                SeedQ("Marked price is ₹800, discount is 15%. Selling Price is:", "अंकित मूल्य ₹800 पर 15% छूट देने पर विक्रय मूल्य होगा:", "₹640", "₹680", "₹700", "₹720", "B", "800 × 0.85 = ₹680.", "800 × 85/100 = ₹680।"),
                SeedQ("If 2x + 3 = 17, find 3x - 5.", "यदि 2x + 3 = 17 हो, तो 3x - 5 का मान बताएं:", "14", "16", "18", "21", "B", "x = 7 => 3(7) - 5 = 16.", "x = 7 अतः 21 - 5 = 16।"),
                SeedQ("Value of (101)² - (99)² is:", "(101)² - (99)² का मान ज्ञात करें:", "200", "300", "400", "500", "C", "(101+99)(101-99) = 200 × 2 = 400.", "200 × 2 = 400।"),
                SeedQ("If ₹4,000 amounts to ₹4,410 in 2 years at CI, rate% is:", "₹4,000 की राशि 2 वर्ष में ₹4,410 हो जाती है। चक्रवृद्धि ब्याज दर बताएं:", "4%", "5%", "6%", "10%", "B", "(1+R/100)² = 441/400 = (21/20)² => R = 5%.", "दर = 5%।"),
                SeedQ("Sum of interior angles of a hexagon is:", "षट्भुज (Hexagon) के सभी अंतःकोणों का योग कितना होता है?", "540°", "720°", "900°", "360°", "B", "(6 - 2) × 180° = 720°.", "(n-2)×180° = 720°।")
            )
            "sainik_c9_cw_math_3d" -> listOf(
                SeedQ("Total surface area of a cube of side 6 cm is:", "6 सेमी भुजा वाले घन का कुल पृष्ठीय क्षेत्रफल है:", "144 cm²", "216 cm²", "180 cm²", "36 cm²", "B", "6 × a² = 6 × 36 = 216 cm².", "6 × 6² = 216 सेमी²।"),
                SeedQ("Volume of cylinder with r = 7 cm, h = 10 cm (π=22/7):", "7 सेमी त्रिज्या और 10 सेमी ऊंचाई वाले बेलन का आयतन:", "1440 cm³", "1540 cm³", "1640 cm³", "1740 cm³", "B", "(22/7) × 7 × 7 × 10 = 1540 cm³.", "πr²h = 1540 सेमी³।"),
                SeedQ("Curved surface area of cylinder is:", "बेलन के वक्र पृष्ठ का क्षेत्रफल सूत्र है:", "πr²h", "2πrh", "2πr(r+h)", "4πr²", "B", "CSA of cylinder = 2πrh.", "2πrh।"),
                SeedQ("Volume of cuboid 5 cm × 4 cm × 3 cm is:", "5 सेमी × 4 सेमी × 3 सेमी घनाभ का आयतन है:", "12 cm³", "30 cm³", "60 cm³", "94 cm³", "C", "5 × 4 × 3 = 60 cm³.", "60 सेमी³।"),
                SeedQ("1 cubic meter equals how many liters?", "1 घन मीटर कितने लीटर के बराबर होता है?", "10 L", "100 L", "1000 L", "10000 L", "C", "1 m³ = 1000 liters.", "1000 लीटर।")
            )
            "sainik_c9_cw_sci_chem", "sainik_c9_sec_sci" -> listOf(
                SeedQ("Which non-metal is essential for combustion?", "दहन के लिए कौन सी अधातु आवश्यक है?", "Nitrogen", "Oxygen", "Carbon", "Argon", "B", "Oxygen supports combustion.", "ऑक्सीजन दहन में सहायक है।"),
                SeedQ("Most ductile metal among the following is:", "सर्वाधिक तन्य (Ductile) धातु कौन सी है?", "Iron", "Gold", "Zinc", "Lead", "B", "Gold is the most ductile metal.", "सोना (Gold) सर्वाधिक तन्य धातु है।"),
                SeedQ("Sudden shaking of Earth is called:", "पृथ्वी के अचानक कांपने को क्या कहते हैं?", "Cyclone", "Earthquake", "Lightning", "Tsunami", "B", "Earthquake (भूकंप).", "भूकंप।"),
                SeedQ("Smallest cell in living organisms is:", "जीवों में सबसे छोटी कोशिका कौन सी है?", "Ostrich egg", "Mycoplasma", "Nerve cell", "Amoeba", "B", "Mycoplasma (PPLO).", "माइकोप्लाज्मा।"),
                SeedQ("Gas produced when metals react with acids:", "धातुओं की अम्ल से अभिक्रिया पर कौन सी गैस निकलती है?", "Oxygen", "Hydrogen", "Nitrogen", "CO₂", "B", "Hydrogen gas is evolved.", "हाइड्रोजन गैस।")
            )
            "sainik_c9_cw_sst_hist" -> listOf(
                SeedQ("Who founded the Indian National Congress in 1885?", "1885 में भारतीय राष्ट्रीय कांग्रेस की स्थापना किसने की?", "A.O. Hume", "Mahatma Gandhi", "Tilak", "Nehru", "A", "A.O. Hume in 1885.", "ए.ओ. ह्यूम ने की।"),
                SeedQ("Who was the first President of India?", "भारत के प्रथम राष्ट्रपति कौन थे?", "Dr. S. Radhakrishnan", "Dr. Rajendra Prasad", "J.L. Nehru", "Zakir Husain", "B", "Dr. Rajendra Prasad.", "डॉ. राजेंद्र प्रसाद।"),
                SeedQ("How many Fundamental Rights are in the Indian Constitution?", "भारतीय संविधान में वर्तमान में कितने मौलिक अधिकार हैं?", "5", "6", "7", "8", "B", "6 Fundamental Rights.", "6 मौलिक अधिकार।"),
                SeedQ("Which soil is best suited for cotton cultivation?", "कपास की खेती के लिए कौन सी मिट्टी सर्वोत्तम है?", "Alluvial soil", "Black soil", "Red soil", "Laterite soil", "B", "Black cotton soil (Regur).", "काली मिट्टी (रेगुर)।"),
                SeedQ("Sepoy Mutiny of 1857 started from:", "1857 की क्रांति की शुरुआत कहाँ से हुई?", "Delhi", "Meerut", "Kanpur", "Jhansi", "B", "Started on 10 May 1857 from Meerut.", "मेरठ से प्रारंभ हुई।")
            )
            "sainik_c9_sec_int" -> listOf(
                SeedQ("If TEACHER = VGCEJGT, then STUDENT =", "यदि TEACHER = VGCEJGT हो, तो STUDENT =", "UVWFGPV", "UVWFHPU", "UTVFGPV", "UWVFGPV", "A", "Each letter shifts +2.", "प्रत्येक अक्षर +2 बढ़ रहा है।"),
                SeedQ("In a row of 40 students, Ram is 13th from left. Find his rank from right.", "40 छात्रों की पंक्ति में राम बाएँ से 13वें स्थान पर है। दाएँ से उसका स्थान बताएं:", "27", "28", "29", "30", "B", "40 - 13 + 1 = 28.", "40 - 13 + 1 = 28।"),
                SeedQ("Find missing: 4, 9, 19, 39, 79, ?", "लुप्त पद ज्ञात करें: 4, 9, 19, 39, 79, ?", "149", "159", "169", "139", "B", "×2 + 1 => 79×2 + 1 = 159.", "×2 + 1 => 159।"),
                SeedQ("Pointing to a photo, Aman said 'He is the only son of my father's father.' How is he related to Aman?", "तस्वीर की ओर इशारा कर अमन ने कहा 'वह मेरे दादाजी का इकलौता पुत्र है।' वह अमन का कौन है?", "Brother", "Father", "Uncle", "Grandfather", "B", "Only son of grandfather is Father.", "पिता।"),
                SeedQ("Odd one out: Copper, Silver, Gold, Plastic", "असंगत चुनें: तांबा, चांदी, सोना, प्लास्टिक", "Copper", "Silver", "Gold", "Plastic", "D", "Plastic is non-metal/insulator.", "प्लास्टिक अधातु है।")
            )
            "sainik_c9_sec_eng" -> listOf(
                SeedQ("Choose correct modal: We ___ obey traffic rules.", "सही modal चुनें: We ___ obey traffic rules.", "must", "may", "might", "could", "A", "'must' expresses duty/obligation.", "अनिवार्य कर्तव्य के लिए 'must'।"),
                SeedQ("Identify the tense: 'She had been waiting for two hours.'", "काल (Tense) पहचानें: 'She had been waiting for two hours.'", "Past Continuous", "Past Perfect", "Past Perfect Continuous", "Present Perfect", "C", "had been + V-ing => Past Perfect Continuous.", "Past Perfect Continuous।"),
                SeedQ("Antonym of 'Optimist' is:", "'Optimist' (आशावादी) का विलोम है:", "Hopeful", "Pessimist", "Cheerful", "Positive", "B", "Pessimist means निराशावादी.", "Pessimist (निराशावादी)।"),
                SeedQ("Fill in the blank: Neither Ram nor Shyam ___ present.", "रिक्त स्थान भरें: Neither Ram nor Shyam ___ present.", "were", "was", "are", "have", "B", "Singular verb agrees with nearest singular subject Shyam.", "was सही है।"),
                SeedQ("One word for 'A person who cannot read or write':", "जो पढ़-लिख न सके उसके लिए एक शब्द:", "Literate", "Illiterate", "Scholar", "Author", "B", "Illiterate (निरक्षर).", "Illiterate।")
            )
            else -> return null
        }
        val marks = if (testId.contains("math") || testId.contains("_fl_")) 3.0 else 2.0
        return buildQuestionPack(testId, marks, items)
    }
}
