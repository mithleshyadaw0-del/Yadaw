package com.example.data.seed

import com.example.data.model.StudyNoteChapter

object StudyNotesSeedData {

    fun getAllNotes(): List<StudyNoteChapter> =
        navodayaNotes() + sainikNotes() + simultalaOlympiadNotes() + CompetitiveStudyNotesSeedData.getNotes()

    fun getNotesForContext(examId: String?, examClass: String?, subject: String? = null): List<StudyNoteChapter> {
        return getAllNotes().filter { note ->
            val matchesExam = examId == null || examId == "All" || note.examCategory.equals(examId, true)
            val matchesClass = examClass.isNullOrBlank() || note.examClass.equals(examClass, true)
            val matchesSubj = subject.isNullOrBlank() || note.subject.equals(subject, true)
            matchesExam && matchesClass && matchesSubj
        }
    }

    fun getSubjectsForNotes(examId: String?, examClass: String?): List<String> {
        val priority = listOf(
            "Mathematics",
            "Reasoning / Mental Ability",
            "Science",
            "English",
            "Hindi / Language",
            "General Knowledge"
        )
        val raw = getNotesForContext(examId, examClass).map { it.subject }.distinct()
        return raw.sortedBy { s -> priority.indexOfFirst { it.equals(s, true) }.let { if (it >= 0) it else Int.MAX_VALUE } }
    }

    private fun navodayaNotes(): List<StudyNoteChapter> = listOf(
        // Navodaya Class 6
        StudyNoteChapter(
            id = "note_jnv6_math_num", examCategory = "Navodaya", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Number System & Place Value", chapterTitleHi = "संख्या पद्धति एवं स्थानीय मान",
            summaryEn = "Covers natural, whole, prime, even/odd numbers, place value vs face value, greatest/smallest digits formation, and divisibility rules (2, 3, 5, 9, 11).",
            summaryHi = "प्राकृत, पूर्ण, सम-विषम व अभाज्य संख्याएं, स्थानीय व जातीय मान, सबसे बड़ी-छोटी संख्या निर्माण तथा 2, 3, 5, 9, 11 से विभाज्यता के नियम।",
            keyFormulas = listOf(
                "Greatest n-digit number = 9 repeated n times; Smallest = 1 followed by (n-1) zeros",
                "Sum of first n natural numbers = n(n + 1) / 2",
                "Dividend = (Divisor × Quotient) + Remainder",
                "Primes between 1 and 100 = 25 (Smallest prime = 2)"
            ),
            solvedExampleEn = "Q: Find the difference between place value and face value of 7 in 47,892.\nSol: Place value = 7,000, Face value = 7 => Difference = 7000 - 7 = 6,993.",
            solvedExampleHi = "प्रश्न: 47,892 में 7 के स्थानीय मान व जातीय मान का अंतर ज्ञात करें।\nहल: 7000 - 7 = 6,993।",
            linkedTestIds = listOf("jnv_c6_cw_math_num", "jnv_c6_sw_math")
        ),
        StudyNoteChapter(
            id = "note_jnv6_math_frac", examCategory = "Navodaya", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Fractions, LCM & HCF", chapterTitleHi = "भिन्न, ल.स. एवं म.स.",
            summaryEn = "Proper/improper fractions, comparison by cross multiplication, decimals conversion, and key HCF × LCM identity for two numbers.",
            summaryHi = "उचित/अनुचित भिन्न, दशमलव रूपांतरण, तथा दो संख्याओं के ल.स. व म.स. के बीच संबंध।",
            keyFormulas = listOf(
                "First Number × Second Number = HCF × LCM",
                "LCM of Fractions = LCM of Numerators / HCF of Denominators",
                "HCF of Fractions = HCF of Numerators / LCM of Denominators"
            ),
            solvedExampleEn = "Q: Product of two numbers is 432 and HCF = 6. Find LCM.\nSol: LCM = 432 / 6 = 72.",
            solvedExampleHi = "प्रश्न: दो संख्याओं का गुणनफल 432 तथा म.स. 6 है। ल.स. बताएं।\nहल: 432 / 6 = 72।",
            linkedTestIds = listOf("jnv_c6_cw_math_frac", "jnv_c6_sec_math")
        ),
        StudyNoteChapter(
            id = "note_jnv6_math_arith", examCategory = "Navodaya", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Percentage, Profit-Loss & Area", chapterTitleHi = "प्रतिशत, लाभ-हानि एवं क्षेत्रफल",
            summaryEn = "Percentage shortcuts, CP/SP/Profit/Loss formulas, Simple Interest, and Perimeter/Area of square and rectangle.",
            summaryHi = "प्रतिशत शॉर्टकट ट्रिक्स, क्रय-विक्रय मूल्य, साधारण ब्याज तथा वर्ग व आयत का परिमाप और क्षेत्रफल।",
            keyFormulas = listOf(
                "Profit % = (Profit / CP) × 100",
                "Simple Interest = (P × R × T) / 100",
                "Area of Square = side², Perimeter = 4 × side"
            ),
            solvedExampleEn = "Q: 20% of a number is 40. Find the number.\nSol: Number = (40 × 100) / 20 = 200.",
            solvedExampleHi = "प्रश्न: किसी संख्या का 20% बराबर 40 है। संख्या ज्ञात करें।\nहल: (40 × 100)/20 = 200।",
            linkedTestIds = listOf("jnv_c6_fl_model", "test_jnv_sainik_03")
        ),
        StudyNoteChapter(
            id = "note_jnv6_mat_series", examCategory = "Navodaya", examClass = "Class 6", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Odd Man Out, Analogy & Number Series", chapterTitleHi = "असंगत छांटना, सादृश्यता व श्रृंखला",
            summaryEn = "Visual & numerical pattern recognition: square/cube series, mirror reflection rules, paper folding, and classification.",
            summaryHi = "आकृति व संख्या श्रृंखला पहचान, वर्ग-घन अंतर, दर्पण प्रतिबिंब व असंगत पद चयन।",
            keyFormulas = listOf(
                "Check constant difference, squares (n²), cubes (n³), or ×2 ± k patterns",
                "Symmetric letters in vertical mirror: A, H, I, M, O, T, U, V, W, X, Y"
            ),
            solvedExampleEn = "Q: Next term in 2, 6, 12, 20, 30, ?\nSol: Differences +4, +6, +8, +10, +12 => 30 + 12 = 42.",
            solvedExampleHi = "प्रश्न: 2, 6, 12, 20, 30, ? का अगला पद:\nहल: अंतर +4, +6, +8, +10, +12 => 42।",
            linkedTestIds = listOf("jnv_c6_cw_mat_series", "jnv_c6_sec_mat")
        ),
        StudyNoteChapter(
            id = "note_jnv6_hin_gram", examCategory = "Navodaya", examClass = "Class 6", subject = "Hindi / Language",
            chapterTitleEn = "पर्यायवाची, विलोम, मुहावरे व अपठित गद्यांश", chapterTitleHi = "हिन्दी व्याकरण एवं शब्द भंडार",
            summaryEn = "High-frequency JNVST Class 6 antonyms, synonyms, one-word substitutions, spelling correction, and comprehension strategy.",
            summaryHi = "नवोदय कक्षा 6 हेतु प्रमुख पर्यायवाची, विलोम, वाक्यांश के लिए एक शब्द, शुद्ध वर्तनी व गद्यांश हल करने की विधि।",
            keyFormulas = listOf(
                "उन्नति ↔ अवनति • ईमानदार ↔ बेईमान • सुलभ ↔ दुर्लभ",
                "सूर्य = दिनकर, भास्कर, रवि • कमल = पंकज, जलज, नीरज"
            ),
            solvedExampleEn = "Q: 'जो सब कुछ जानता हो' — एक शब्द?\nAns: सर्वज्ञ (Omniscient).",
            solvedExampleHi = "प्रश्न: 'जो सब कुछ जानता हो' के लिए एक शब्द:\nउत्तर: सर्वज्ञ।",
            linkedTestIds = listOf("jnv_c6_cw_hin_gram", "jnv_c6_sec_hin")
        ),

        // Navodaya Class 9
        StudyNoteChapter(
            id = "note_jnv9_math_rat", examCategory = "Navodaya", examClass = "Class 9", subject = "Mathematics",
            chapterTitleEn = "Rational Numbers, Exponents & Powers", chapterTitleHi = "परिमेय संख्याएं एवं घातांक",
            summaryEn = "Laws of exponents, negative powers, additive/multiplicative inverses, and standard scientific notation.",
            summaryHi = "घातांक के नियम, ऋणात्मक घात, योज्य व गुणात्मक प्रतिलोम तथा मानक रूप।",
            keyFormulas = listOf(
                "aᵐ × aⁿ = aᵐ⁺ⁿ ; aᵐ ÷ aⁿ = aᵐ⁻ⁿ",
                "a⁻ⁿ = 1 / aⁿ ; a⁰ = 1 (a ≠ 0)",
                "Additive inverse of a/b is -a/b; Multiplicative inverse is b/a"
            ),
            solvedExampleEn = "Q: Find 2⁻³ × 2⁵.\nSol: 2^(-3+5) = 2² = 4.",
            solvedExampleHi = "प्रश्न: 2⁻³ × 2⁵ का मान:\nहल: 2² = 4।",
            linkedTestIds = listOf("jnv_c9_cw_math_rat", "jnv_c9_sw_math")
        ),
        StudyNoteChapter(
            id = "note_jnv9_math_alg", examCategory = "Navodaya", examClass = "Class 9", subject = "Mathematics",
            chapterTitleEn = "Squares, Cubes & Algebraic Identities", chapterTitleHi = "वर्ग, घन एवं बीजीय सर्वसमिकाएं",
            summaryEn = "Factorization using standard identities, non-square numbers count between n² and (n+1)², and cube root estimation.",
            summaryHi = "बीजीय सर्वसमिकाओं से गुणनखंड, वर्ग व घनमूल निकालने की ट्रिक्स।",
            keyFormulas = listOf(
                "(a + b)² = a² + 2ab + b² ; a² - b² = (a + b)(a - b)",
                "Non-square numbers between n² and (n+1)² = 2n",
                "If x - 1/x = k, then x² + 1/x² = k² + 2"
            ),
            solvedExampleEn = "Q: If x - 1/x = 5, find x² + 1/x².\nSol: 5² + 2 = 27.",
            solvedExampleHi = "प्रश्न: x - 1/x = 5 तो x² + 1/x² = ?\nहल: 25 + 2 = 27।",
            linkedTestIds = listOf("jnv_c9_cw_math_alg", "jnv_c9_sec_math")
        ),
        StudyNoteChapter(
            id = "note_jnv9_sci_force", examCategory = "Navodaya", examClass = "Class 9", subject = "Science",
            chapterTitleEn = "Force, Pressure, Sound & Light", chapterTitleHi = "बल, दाब, ध्वनि एवं प्रकाश",
            summaryEn = "SI units, liquid & atmospheric pressure, sound propagation in solids/liquids/gases, and dispersion of light.",
            summaryHi = "SI मात्रक, बल व दाब, ध्वनि की चाल, वर्ण विक्षेपण व सूक्ष्मजीव।",
            keyFormulas = listOf(
                "Pressure = Force / Area (Unit: Pascal = N/m²)",
                "Speed of Sound: Solids > Liquids > Gases (0 in Vacuum)",
                "Frequency = 1 / Time Period (Unit: Hertz)"
            ),
            solvedExampleEn = "Q: Why does a sharp knife cut better?\nSol: Smaller contact area increases Pressure for the same Force.",
            solvedExampleHi = "प्रश्न: तेज चाकू से आसानी से क्यों कटता है?\nहल: क्षेत्रफल कम होने से दाब बढ़ जाता है।",
            linkedTestIds = listOf("jnv_c9_cw_sci_force", "jnv_c9_sw_sci")
        ),
        StudyNoteChapter(
            id = "note_jnv9_eng_gram", examCategory = "Navodaya", examClass = "Class 9", subject = "English",
            chapterTitleEn = "Tense, Active-Passive Voice & Vocab", chapterTitleHi = "Tense, Voice एवं शब्दावली",
            summaryEn = "Subject-verb agreement, passive voice transformations across tenses, and common spelling/antonym pairs.",
            summaryHi = "अंग्रेजी व्याकरण के मुख्य नियम, Active-Passive परिवर्तन व Synonyms/Antonyms।",
            keyFormulas = listOf(
                "Simple Present Passive: Object + is/am/are + V3 + by + Subject",
                "'i' before 'e' except after 'c' (e.g. Receive, Believe)"
            ),
            solvedExampleEn = "Q: Passive of 'Ram writes a letter.'\nAns: A letter is written by Ram.",
            solvedExampleHi = "प्रश्न: 'Ram writes a letter.' का Passive:\nउत्तर: A letter is written by Ram.",
            linkedTestIds = listOf("jnv_c9_cw_eng_gram", "jnv_c9_sec_eng")
        ),
        StudyNoteChapter(
            id = "note_jnv9_hin_sandhi", examCategory = "Navodaya", examClass = "Class 9", subject = "Hindi / Language",
            chapterTitleEn = "संधि, समास एवं वाक्य शुद्धि", chapterTitleHi = "संधि व समास (कक्षा 9)",
            summaryEn = "स्वर संधि के भेद (दीर्घ, गुण, वृद्धि, यण), समास के 6 भेद तथा प्रमुख मुहावरे।",
            summaryHi = "नवोदय कक्षा 9 हिन्दी पाठ्यक्रम: संधि विच्छेद, समास पहचान और शुद्ध वर्तनी।",
            keyFormulas = listOf(
                "दीर्घ संधि: अ/आ + अ/आ = आ (विद्या + आलय = विद्यालय)",
                "तत्पुरुष समास: उत्तर पद प्रधान व कारक चिह्न का लोप (रसोई के लिए घर)"
            ),
            solvedExampleEn = "Q: 'रसोईघर' में कौन सा समास है?\nउत्तर: संप्रदान तत्पुरुष समास।",
            solvedExampleHi = "प्रश्न: 'रसोईघर' में कौन सा समास है?\nउत्तर: संप्रदान तत्पुरुष।",
            linkedTestIds = listOf("jnv_c9_cw_hin_sandhi", "jnv_c9_sec_hin")
        )
    )

    private fun sainikNotes(): List<StudyNoteChapter> = listOf(
        // Sainik Class 6
        StudyNoteChapter(
            id = "note_sai6_math_unit", examCategory = "Sainik School", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Unitary Method, Average & Ratio", chapterTitleHi = "ऐकिक नियम, औसत एवं अनुपात",
            summaryEn = "Direct vs inverse proportion (workers & days), average shortcuts, ratio simplification after unit conversion, and Speed-Distance.",
            summaryHi = "सीधा व व्युत्क्रमानुपात, औसत के सूत्र, इकाई समान कर अनुपात निकालना व चाल-समय।",
            keyFormulas = listOf(
                "Average = Sum of Observations / Number of Observations",
                "Inverse Proportion (Workers): M₁ × D₁ = M₂ × D₂",
                "Speed = Distance / Time"
            ),
            solvedExampleEn = "Q: 8 workers finish wall in 6 days. Days for 12 workers?\nSol: 8 × 6 / 12 = 4 days.",
            solvedExampleHi = "प्रश्न: 8 मजदूर 6 दिन में काम करें, तो 12 मजदूर?\nहल: (8 × 6)/12 = 4 दिन।",
            linkedTestIds = listOf("sainik_c6_cw_math_unit", "sainik_c6_sw_math")
        ),
        StudyNoteChapter(
            id = "note_sai6_math_geom", examCategory = "Sainik School", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Angles, Triangles & Circle Geometry", chapterTitleHi = "कोण, त्रिभुज, क्षेत्रफल व वृत्त",
            summaryEn = "Acute/Right/Obtuse angles, Complementary (90°) and Supplementary (180°) pairs, triangle angle sum, and diameter/radius relation.",
            summaryHi = "कोणों के प्रकार, पूरक (90°) व संपूरक (180°) कोण, आयत क्षेत्रफल तथा वृत्त का व्यास।",
            keyFormulas = listOf(
                "Complement of θ = 90° - θ ; Supplement of θ = 180° - θ",
                "Sum of interior angles of triangle = 180°",
                "Diameter = 2 × Radius"
            ),
            solvedExampleEn = "Q: Find supplement of 110°.\nSol: 180° - 110° = 70°.",
            solvedExampleHi = "प्रश्न: 110° का संपूरक कोण?\nहल: 180° - 110° = 70°।",
            linkedTestIds = listOf("sainik_c6_cw_math_geom", "sainik_c6_sec_math")
        ),
        StudyNoteChapter(
            id = "note_sai6_int_dir", examCategory = "Sainik School", examClass = "Class 6", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Direction Sense & Coding-Decoding", chapterTitleHi = "दिशा ज्ञान, कोडिंग व संख्या श्रृंखला",
            summaryEn = "4 cardinal directions, left/right 90° turns, alphabet position values (A=1..Z=26), and 2D vs 3D classification.",
            summaryHi = "मुख्य दिशाएं, दाएं-बाएं मुड़ना, अक्षर क्रम (EJOTY) व श्रृंखला परीक्षण।",
            keyFormulas = listOf(
                "EJOTY Positions: E=5, J=10, O=15, T=20, Y=25",
                "Clockwise from North: North -> East -> South -> West"
            ),
            solvedExampleEn = "Q: Facing North, turn Right. New direction?\nAns: East.",
            solvedExampleHi = "प्रश्न: उत्तर से दाएं मुड़ने पर कौन सी दिशा?\nउत्तर: पूर्व (East)।",
            linkedTestIds = listOf("sainik_c6_cw_int_dir", "sainik_c6_sec_int")
        ),
        StudyNoteChapter(
            id = "note_sai6_gk_def", examCategory = "Sainik School", examClass = "Class 6", subject = "General Knowledge",
            chapterTitleEn = "Indian Defence Forces, Awards & Planets", chapterTitleHi = "रक्षा बल, पुरस्कार व सौरमंडल",
            summaryEn = "Gallantry awards (Param Vir Chakra, Ashoka Chakra), Army/Navy/Air Force days, National symbols, and Solar System facts.",
            summaryHi = "परमवीर चक्र, सेना दिवस (15 जन/8 अक्टू/4 दिस), राष्ट्रीय प्रतीक तथा सौरमंडल के ग्रह।",
            keyFormulas = listOf(
                "Army Day: 15 Jan | Air Force Day: 8 Oct | Navy Day: 4 Dec",
                "Highest Wartime Award: Param Vir Chakra; Red Planet: Mars"
            ),
            solvedExampleEn = "Q: National Aquatic Animal of India?\nAns: Ganges River Dolphin.",
            solvedExampleHi = "प्रश्न: भारत का राष्ट्रीय जलीय जीव?\nउत्तर: गंगा डॉल्फिन।",
            linkedTestIds = listOf("sainik_c6_cw_gk_def", "sainik_c6_sw_gk")
        ),
        StudyNoteChapter(
            id = "note_sai6_eng_pos", examCategory = "Sainik School", examClass = "Class 6", subject = "English",
            chapterTitleEn = "Articles, Prepositions & Parts of Speech", chapterTitleHi = "Articles, Prepositions व वचन",
            summaryEn = "Using 'a/an' by vowel sound (e.g. an honest man), surface prepositions (on/in/at), irregular plurals, and verb tenses.",
            summaryHi = "ध्वनि के अनुसार Articles (a/an/the), Prepositions तथा बहुवचन व क्रिया के रूप।",
            keyFormulas = listOf(
                "Use 'an' before vowel SOUND (an honest, an hour, a university)",
                "Irregular Plurals: Child -> Children, Tooth -> Teeth"
            ),
            solvedExampleEn = "Q: He is ___ honest officer.\nAns: an (silent 'h', vowel sound).",
            solvedExampleHi = "प्रश्न: He is ___ honest officer.\nउत्तर: an।",
            linkedTestIds = listOf("sainik_c6_cw_eng_pos", "sainik_c6_sec_eng")
        ),

        // Sainik Class 9
        StudyNoteChapter(
            id = "note_sai9_math_ci", examCategory = "Sainik School", examClass = "Class 9", subject = "Mathematics",
            chapterTitleEn = "Compound Interest, Discount & Polygons", chapterTitleHi = "चक्रवृद्धि ब्याज, बट्टा व बहुभुज",
            summaryEn = "Annual CI formula, Marked Price discount computation, polygon interior/exterior angle sums, and difference of squares.",
            summaryHi = "चक्रवृद्धि ब्याज, छूट, बहुभुज के कोणों का योग तथा a²-b² सर्वसमिका।",
            keyFormulas = listOf(
                "Amount A = P(1 + R/100)ⁿ ; CI = A - P",
                "Sum of interior angles of n-gon = (n - 2) × 180°",
                "SP = MP × (100 - Discount%) / 100"
            ),
            solvedExampleEn = "Q: CI on ₹10,000 at 10% for 2 years.\nSol: 10000×(1.1)² - 10000 = ₹2,100.",
            solvedExampleHi = "प्रश्न: ₹10,000 पर 10% से 2 वर्ष का CI?\nहल: ₹2,100।",
            linkedTestIds = listOf("sainik_c9_cw_math_ci", "sainik_c9_sw_math")
        ),
        StudyNoteChapter(
            id = "note_sai9_math_3d", examCategory = "Sainik School", examClass = "Class 9", subject = "Mathematics",
            chapterTitleEn = "3D Mensuration: Cube, Cuboid & Cylinder", chapterTitleHi = "घन, घनाभ व बेलन का क्षेत्रफल-आयतन",
            summaryEn = "TSA, LSA/CSA, Volume formulas, diagonal of cube, and m³ to litres unit conversion.",
            summaryHi = "घन, घनाभ व बेलन के वक्र पृष्ठ, सम्पूर्ण पृष्ठ और आयतन के सूत्र।",
            keyFormulas = listOf(
                "Cube: Volume = a³, TSA = 6a², Diagonal = a√3",
                "Cylinder: Volume = πr²h, CSA = 2πrh, TSA = 2πr(r+h)",
                "1 m³ = 1,000 Litres"
            ),
            solvedExampleEn = "Q: Volume of cylinder r=7 cm, h=10 cm.\nSol: (22/7)×7×7×10 = 1540 cm³.",
            solvedExampleHi = "प्रश्न: r=7 सेमी, h=10 सेमी बेलन का आयतन?\nहल: 1540 सेमी³।",
            linkedTestIds = listOf("sainik_c9_cw_math_3d", "sainik_c9_sec_math")
        ),
        StudyNoteChapter(
            id = "note_sai9_sci_chem", examCategory = "Sainik School", examClass = "Class 9", subject = "Science",
            chapterTitleEn = "Metals, Non-Metals & Cell Biology", chapterTitleHi = "धातु-अधातु, दहन व कोशिका विज्ञान",
            summaryEn = "Ductility/malleability, metal+acid -> H₂ gas, cell organelles (Mitochondria, PPLO), and earthquake/combustion basics.",
            summaryHi = "धातुओं के गुण, अम्ल से हाइड्रोजन गैस, कोशिका का ऊर्जा गृह माइटोकॉन्ड्रिया व दहन।",
            keyFormulas = listOf(
                "Metal + Dilute Acid -> Salt + Hydrogen (H₂) Gas",
                "Powerhouse of Cell: Mitochondria | Smallest Cell: Mycoplasma"
            ),
            solvedExampleEn = "Q: Most ductile metal?\nAns: Gold (1g can draw 2 km wire).",
            solvedExampleHi = "प्रश्न: सर्वाधिक तन्य धातु?\nउत्तर: सोना (Gold)।",
            linkedTestIds = listOf("sainik_c9_cw_sci_chem", "sainik_c9_sec_sci")
        ),
        StudyNoteChapter(
            id = "note_sai9_gk_hist", examCategory = "Sainik School", examClass = "Class 9", subject = "General Knowledge",
            chapterTitleEn = "Modern History, Constitution & Soils", chapterTitleHi = "आधुनिक इतिहास, संविधान व मिट्टी",
            summaryEn = "1857 Revolt (Meerut), INC 1885 (A.O. Hume), Battle of Plassey 1757, 6 Fundamental Rights, and Black cotton soil.",
            summaryHi = "1857 की क्रांति, कांग्रेस स्थापना, प्लासी युद्ध, 6 मौलिक अधिकार व काली मिट्टी।",
            keyFormulas = listOf(
                "Plassey: 1757 | 1857 Revolt: 10 May Meerut | INC: 1885",
                "Currently 6 Fundamental Rights in Part III of Constitution"
            ),
            solvedExampleEn = "Q: Best soil for cotton cultivation?\nAns: Black Soil (Regur Soil).",
            solvedExampleHi = "प्रश्न: कपास के लिए सर्वोत्तम मिट्टी?\nउत्तर: काली (रेगुर) मिट्टी।",
            linkedTestIds = listOf("sainik_c9_cw_sst_hist")
        ),
        StudyNoteChapter(
            id = "note_sai9_int_cod", examCategory = "Sainik School", examClass = "Class 9", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Ranking, Blood Relations & Coding", chapterTitleHi = "रैंकिंग, रक्त संबंध व कोडिंग",
            summaryEn = "Row rank formula (Total = L + R - 1), letter shift coding, and blood relation tree mapping.",
            summaryHi = "पंक्ति में स्थान ज्ञात करना, अक्षर कोडिंग तथा रक्त संबंध।",
            keyFormulas = listOf(
                "Total Persons = Left Rank + Right Rank - 1",
                "Right Rank = Total - Left Rank + 1"
            ),
            solvedExampleEn = "Q: Row of 40, Ram is 13th from left. Rank from right?\nSol: 40 - 13 + 1 = 28th.",
            solvedExampleHi = "प्रश्न: 40 की पंक्ति में बाएं से 13वां स्थान, दाएं से?\nहल: 40 - 13 + 1 = 28वां।",
            linkedTestIds = listOf("sainik_c9_sec_int")
        ),
        StudyNoteChapter(
            id = "note_sai9_eng_mod", examCategory = "Sainik School", examClass = "Class 9", subject = "English",
            chapterTitleEn = "Modals, Direct-Indirect & Subject-Verb", chapterTitleHi = "Modals, Narration व Grammar",
            summaryEn = "Duty modal 'must', Past Perfect Continuous (had been + V-ing), Neither-Nor agreement with nearest subject.",
            summaryHi = "अंग्रेजी व्याकरण: Modals, Indirect Speech व Subject-Verb Agreement।",
            keyFormulas = listOf(
                "Neither A nor B -> Verb agrees with B (nearest subject)",
                "Reported speech: Present tense shifts to Past tense"
            ),
            solvedExampleEn = "Q: Neither Ram nor Shyam ___ present.\nAns: was (agrees with singular Shyam).",
            solvedExampleHi = "प्रश्न: Neither Ram nor Shyam ___ present.\nउत्तर: was।",
            linkedTestIds = listOf("sainik_c9_sec_eng")
        )
    )

    private fun simultalaOlympiadNotes(): List<StudyNoteChapter> = listOf(
        // Simultala Class 6
        StudyNoteChapter(
            id = "note_sim6_math_pct", examCategory = "Simultala", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Percentage, Profit & Simple Interest", chapterTitleHi = "प्रतिशत, लाभ-हानि व साधारण ब्याज",
            summaryEn = "Decimal to percentage conversion, Profit/Loss %, Simple Interest, and natural numbers average for Bihar SAV Prelims & Mains.",
            summaryHi = "सिमुलतला कक्षा 6 गणित: प्रतिशत, क्रय-विक्रय मूल्य, साधारण ब्याज व प्राकृत संख्या औसत।",
            keyFormulas = listOf(
                "SI = (P × R × T) / 100 ; Profit% = (SP - CP)/CP × 100",
                "Average of first n natural numbers = (n + 1) / 2"
            ),
            solvedExampleEn = "Q: SI on ₹2,000 at 5% for 4 years.\nSol: (2000 × 5 × 4)/100 = ₹400.",
            solvedExampleHi = "प्रश्न: ₹2000 पर 5% से 4 वर्ष का साधारण ब्याज?\nहल: ₹400।",
            linkedTestIds = listOf("sim_c6_cw_math_pct", "sim_c6_sw_math")
        ),
        StudyNoteChapter(
            id = "note_sim6_sci_plant", examCategory = "Simultala", examClass = "Class 6", subject = "Science",
            chapterTitleEn = "Plants, Photosynthesis & Nutrition", chapterTitleHi = "पौधे, पोषण एवं मानव शरीर",
            summaryEn = "Chlorophyll function, Vitamin deficiencies (C -> Scurvy, D -> Sunlight), human skeletal count (206), and water boiling point (100°C).",
            summaryHi = "प्रकाश संश्लेषण, विटामिन की कमी से रोग, हड्डियों की संख्या (206) व जल का क्वथनांक।",
            keyFormulas = listOf(
                "Photosynthesis: 6CO₂ + 6H₂O --(Sunlight/Chlorophyll)--> Glucose + 6O₂",
                "Vit A: Night Blindness | Vit B: Beriberi | Vit C: Scurvy | Vit D: Rickets"
            ),
            solvedExampleEn = "Q: Which gas do plants absorb for photosynthesis?\nAns: Carbon dioxide (CO₂).",
            solvedExampleHi = "प्रश्न: पौधे प्रकाश संश्लेषण में कौन सी गैस लेते हैं?\nउत्तर: CO₂।",
            linkedTestIds = listOf("sim_c6_cw_sci_plant", "sim_c6_sec_sci")
        ),
        StudyNoteChapter(
            id = "note_sim6_gk_bihar", examCategory = "Simultala", examClass = "Class 6", subject = "General Knowledge",
            chapterTitleEn = "Bihar Heritage & Indian Geography", chapterTitleHi = "बिहार सामान्य ज्ञान व भारतीय भूगोल",
            summaryEn = "Jamui (SAV location), Kosi river (Sorrow of Bihar), Bodh Gaya, Ancient Nalanda, Bihar symbols (Peepal), and state borders.",
            summaryHi = "सिमुलतला (जमुई), कोसी नदी, बोधगया, नालंदा विश्वविद्यालय व बिहार के राजकीय प्रतीक।",
            keyFormulas = listOf(
                "Sorrow of Bihar: Kosi River | State Tree: Peepal",
                "Bihar shares border with UP, Jharkhand, West Bengal & Nepal"
            ),
            solvedExampleEn = "Q: Where is Ancient Nalanda University?\nAns: Nalanda district, Bihar.",
            solvedExampleHi = "प्रश्न: प्राचीन नालंदा विश्वविद्यालय कहाँ है?\nउत्तर: बिहार।",
            linkedTestIds = listOf("sim_c6_cw_gk_bihar", "sim_c6_sec_gk")
        ),
        StudyNoteChapter(
            id = "note_sim6_hin_karak", examCategory = "Simultala", examClass = "Class 6", subject = "Hindi / Language",
            chapterTitleEn = "संज्ञा, सर्वनाम, विशेषण व कारक", chapterTitleHi = "संज्ञा, सर्वनाम व कारक भेद",
            summaryEn = "संज्ञा के भेद, सर्वनाम के 6 भेद, विशेषण व 8 कारक चिह्नों (कर्ता ने, कर्म को, अपादान से-अलगाव) की पहचान।",
            summaryHi = "हिन्दी व्याकरण कक्षा 6 सिमुलतला पाठ्यक्रम।",
            keyFormulas = listOf(
                "अपादान कारक ('से' अलग होने का भाव): पेड़ से पत्ता गिरा",
                "सर्वनाम के 6 भेद व कारक के 8 भेद होते हैं"
            ),
            solvedExampleEn = "Q: 'हिमालय' कौन सा शब्द भेद है?\nउत्तर: व्यक्तिवाचक संज्ञा।",
            solvedExampleHi = "प्रश्न: 'पेड़ से पत्ता गिरा' में कारक?\nउत्तर: अपादान कारक।",
            linkedTestIds = listOf("sim_c6_cw_hin_karak", "sim_c6_sec_hin")
        ),

        // Olympiad Class 6
        StudyNoteChapter(
            id = "note_oly6_math_logic", examCategory = "Olympiad", examClass = "Class 6", subject = "Mathematics",
            chapterTitleEn = "Number Puzzles, Units Digit & Patterns", chapterTitleHi = "संख्या पहेलियाँ, इकाई अंक व सममिति",
            summaryEn = "Cryptarithm deduction, modular remainders, units digit cycles, clock angle formula, and sum of first n even numbers.",
            summaryHi = "ओलंपियाड तार्किक गणित: इकाई अंक चक्र, घड़ी कोण व सम संख्या योग।",
            keyFormulas = listOf(
                "Sum of first n even numbers = n(n + 1)",
                "7⁴ mod 10 = 1 (cyclicity of 7 is 4: 7, 9, 3, 1)",
                "Line of symmetry of regular n-sided polygon = n"
            ),
            solvedExampleEn = "Q: Sum of first 10 positive even numbers.\nSol: 10 × 11 = 110.",
            solvedExampleHi = "प्रश्न: प्रथम 10 सम संख्याओं का योग?\nहल: 10 × 11 = 110।",
            linkedTestIds = listOf("oly_c6_cw_math_logic", "oly_c6_sw_math")
        ),
        StudyNoteChapter(
            id = "note_oly6_reas_log", examCategory = "Olympiad", examClass = "Class 6", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Mirror Images, Coding & Calendar", chapterTitleHi = "दर्पण प्रतिबिंब, कोडिंग व कैलेंडर",
            summaryEn = "Day of week modulo 7 trick, rank inversion formula, alphabet shift coding, and mirror symmetry.",
            summaryHi = "तर्कशक्ति: कैलेंडर दिन शेषफल, कोडिंग-डिकोडिंग व दर्पण प्रतिबिंब।",
            keyFormulas = listOf(
                "Add days mod 7 to get weekday quickly",
                "Rank from bottom = Total - Top Rank + 1"
            ),
            solvedExampleEn = "Q: Monday + 10 days = ?\nSol: 10 mod 7 = 3 => Mon + 3 = Thursday.",
            solvedExampleHi = "प्रश्न: सोमवार + 10 दिन = ?\nहल: शेष 3 => गुरुवार।",
            linkedTestIds = listOf("oly_c6_cw_reas_log")
        ),
        StudyNoteChapter(
            id = "note_oly6_sci_nso", examCategory = "Olympiad", examClass = "Class 6", subject = "Science",
            chapterTitleEn = "Electricity, Light & Food Nutrients", chapterTitleHi = "विद्युत, प्रकाश व पोषक तत्व",
            summaryEn = "Ammeter vs Voltmeter, conductors/insulators, plane mirror image properties (virtual & erect), and carbohydrate energy.",
            summaryHi = "NSO विज्ञान: अमीटर, सुचालक-कुचालक, समतल दर्पण व कार्बोहाइड्रेट।",
            keyFormulas = listOf(
                "Ammeter measures Current (Amperes) in series",
                "Plane Mirror Image: Virtual, Erect, Same Size, Laterally Inverted"
            ),
            solvedExampleEn = "Q: Good conductor of electricity among Copper, Rubber, Plastic?\nAns: Copper.",
            solvedExampleHi = "प्रश्न: तांबा, रबड़, प्लास्टिक में सुचालक?\nउत्तर: तांबा।",
            linkedTestIds = listOf("oly_c6_sec_sci")
        ),

        // Olympiad Class 9
        StudyNoteChapter(
            id = "note_oly9_math_poly", examCategory = "Olympiad", examClass = "Class 9", subject = "Mathematics",
            chapterTitleEn = "Polynomials, Remainder & Quadratic", chapterTitleHi = "बहुपद, शेषफल प्रमेय व द्विघात",
            summaryEn = "Remainder theorem P(a), conditional cube identity (a+b+c=0 => 3abc), coordinate quadrants, and Vieta's root relations.",
            summaryHi = "शेषफल प्रमेय, बहुपद सर्वसमिका, चतुर्थांश व मूलों का योग-गुणनफल।",
            keyFormulas = listOf(
                "Remainder when P(x) divided by (x - a) is P(a)",
                "If a + b + c = 0, then a³ + b³ + c³ = 3abc",
                "For ax² + bx + c = 0: α+β = -b/a, αβ = c/a"
            ),
            solvedExampleEn = "Q: Remainder when x³ - 3x² + 4 divided by (x - 2).\nSol: P(2) = 8 - 12 + 4 = 0.",
            solvedExampleHi = "प्रश्न: x³-3x²+4 को (x-2) से भाग देने पर शेष?\nहल: P(2) = 0।",
            linkedTestIds = listOf("oly_c9_cw_math_poly", "oly_c9_sw_math")
        ),
        StudyNoteChapter(
            id = "note_oly9_sci_laws", examCategory = "Olympiad", examClass = "Class 9", subject = "Science",
            chapterTitleEn = "Laws of Motion, Work & Gravitation", chapterTitleHi = "गति के नियम, कार्य व गुरुत्वाकर्षण",
            summaryEn = "Momentum p=mv, Inertia & Mass, Work-Energy Joule unit, gravitational acceleration g≈9.8 m/s², and 1 HP = 746 W.",
            summaryHi = "संवेग, जड़त्व, कार्य-ऊर्जा (जूल), गुरुत्वीय त्वरण व अश्वशक्ति।",
            keyFormulas = listOf(
                "Momentum p = m × v ; Force F = m × a",
                "1 Horsepower (HP) = 746 Watts ; g ≈ 9.8 m/s²"
            ),
            solvedExampleEn = "Q: Inertia depends on?\nAns: Mass of the object.",
            solvedExampleHi = "प्रश्न: वस्तु का जड़त्व किस पर निर्भर करता है?\nउत्तर: द्रव्यमान पर।",
            linkedTestIds = listOf("oly_c9_cw_sci_laws")
        ),
        StudyNoteChapter(
            id = "note_oly9_reas_adv", examCategory = "Olympiad", examClass = "Class 9", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Venn Diagrams, Clocks & Painted Cubes", chapterTitleHi = "वेन आरेख, घड़ी कोण व रंगे घन",
            summaryEn = "Clock hand angle |30H - 5.5M|, painted cube corner formula (always 8 with 3 faces painted), and inclusion-exclusion principle.",
            summaryHi = "घड़ी कोण सूत्र, 27 घनों का कटान तथा समुच्चय वेन आरेख।",
            keyFormulas = listOf(
                "Clock Angle θ = |30H - 5.5M|",
                "n(A ∪ B) = n(A) + n(B) - n(A ∩ B)",
                "3-face painted corner cubes in any n×n×n cube = Always 8"
            ),
            solvedExampleEn = "Q: Acute angle at 4:30?\nSol: |30(4) - 5.5(30)| = |120 - 165| = 45°.",
            solvedExampleHi = "प्रश्न: 4:30 बजे घड़ी की सुइयों के बीच कोण?\nहल: 45°।",
            linkedTestIds = listOf("oly_c9_sec_reas")
        )
    )
}
