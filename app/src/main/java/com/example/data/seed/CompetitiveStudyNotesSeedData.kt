package com.example.data.seed

import com.example.data.model.StudyNoteChapter

object CompetitiveStudyNotesSeedData {

    fun getNotes(): List<StudyNoteChapter> = listOf(
        // ==================== RAILWAY CBT ====================
        StudyNoteChapter(
            id = "note_rail_math_tsd", examCategory = "Railway", examClass = "", subject = "Mathematics",
            chapterTitleEn = "Time, Speed, Distance & Train Tricks", chapterTitleHi = "चाल, समय, दूरी व रेलगाड़ी शॉर्टकट्स",
            summaryEn = "Instant km/h ↔ m/s conversion, platform/pole crossing lengths, downstream/upstream boat speed, and 2xy/(x+y) average speed.",
            summaryHi = "किमी/घंटा से मी/से रूपांतरण, ट्रेन-प्लेटफॉर्म पार करना, नाव व धारा तथा औसत चाल ट्रिक्स।",
            keyFormulas = listOf(
                "km/h × 5/18 = m/s  |  m/s × 18/5 = km/h",
                "Train + Platform crossing time = (L₁ + L₂) / Relative Speed",
                "Average Speed (equal distance) = 2xy / (x + y)"
            ),
            solvedExampleEn = "Q: Train 240m crosses 160m platform in 20s. Speed?\nSol: (400/20) = 20 m/s = 20 × 18/5 = 72 km/h.",
            solvedExampleHi = "प्रश्न: 240मी ट्रेन 160मी प्लेटफॉर्म 20से में पार करे तो चाल?\nहल: 400/20 = 20 मी/से = 72 किमी/घंटा।",
            linkedTestIds = listOf("rail_cw_math_tsd", "rail_sw_math", "test_railway_math_01")
        ),
        StudyNoteChapter(
            id = "note_rail_math_tw", examCategory = "Railway", examClass = "", subject = "Mathematics",
            chapterTitleEn = "Time & Work, Pipes & Cisterns (LCM Method)", chapterTitleHi = "समय-कार्य व नल-टंकी (LCM मेथड)",
            summaryEn = "Mithlesh Sir's signature LCM method for combined work, pipe filling/emptying net rate, and MDH/wages distribution.",
            summaryHi = "मिथलेश सर की LCM विधि से समय-कार्य, टंकी व मजदूरी विभाजन।",
            keyFormulas = listOf(
                "A & B together = (A × B) / (A + B) days",
                "Fill A & Empty B = (A × B) / (B - A) hours",
                "M₁D₁H₁ / W₁ = M₂D₂H₂ / W₂"
            ),
            solvedExampleEn = "Q: A fills in 10h, B empties in 15h. Net fill time?\nSol: (10×15)/(15-10) = 30 hours.",
            solvedExampleHi = "प्रश्न: A 10घं में भरे, B 15घं में खाली करे तो समय?\nहल: 30 घंटे।",
            linkedTestIds = listOf("rail_cw_math_tw", "rail_sec_math")
        ),
        StudyNoteChapter(
            id = "note_rail_reas_syl", examCategory = "Railway", examClass = "", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Syllogism, Blood Relation & Number Coding", chapterTitleHi = "न्याय निगमन, रक्त संबंध व कोडिंग",
            summaryEn = "Venn method for Some/All/No conclusions, maternal/paternal relation chains, and ASCII/alphabet rank coding.",
            summaryHi = "कथन-निष्कर्ष, पारिवारिक रक्त संबंध व अक्षर योग कोडिंग।",
            keyFormulas = listOf(
                "If 'Some A are B' is true, 'Some B are A' always follows",
                "Sum letter positions: A=1, B=2, ..., Z=26"
            ),
            solvedExampleEn = "Q: BOOK = 43. Find PEN.\nSol: P(16) + E(5) + N(14) = 35.",
            solvedExampleHi = "प्रश्न: BOOK=43 तो PEN=?\nहल: 16+5+14 = 35।",
            linkedTestIds = listOf("rail_cw_reas_syl", "rail_sec_reas")
        ),
        StudyNoteChapter(
            id = "note_rail_sci_phy", examCategory = "Railway", examClass = "", subject = "Science",
            chapterTitleEn = "RRB Special Physics, Optics & Chemistry", chapterTitleHi = "रेलवे विज्ञान: लेंस, प्रतिरोध व सूत्र",
            summaryEn = "Lens power (Dioptre), Myopia/Hypermetropia defects, Resistance (Ohm), pH scale, Baking Soda, and Sodium safety.",
            summaryHi = "लेंस क्षमता, दृष्टि दोष, ओम नियम, pH मान तथा प्रमुख रासायनिक सूत्र।",
            keyFormulas = listOf(
                "Power of Lens P = 1 / f(m) in Dioptres (D)",
                "Myopia -> Concave Lens | Hypermetropia -> Convex Lens",
                "Baking Soda: NaHCO₃ | Heavy Water: D₂O"
            ),
            solvedExampleEn = "Q: Which mirror is used as rear-view in cars?\nAns: Convex Mirror.",
            solvedExampleHi = "प्रश्न: वाहनों में पीछे देखने वाला दर्पण?\nउत्तर: उत्तल दर्पण।",
            linkedTestIds = listOf("rail_cw_sci_phy", "rail_sw_sci", "rail_sec_sci")
        ),
        StudyNoteChapter(
            id = "note_rail_gk_ind", examCategory = "Railway", examClass = "", subject = "General Knowledge",
            chapterTitleEn = "Railway Zones, Polity & Geography", chapterTitleHi = "रेलवे जोन, संविधान व राष्ट्रीय उद्यान",
            summaryEn = "Railway HQs (ECR Hajipur), First Railway Minister (John Mathai), Articles 17/32, and Kaziranga/Rivers GK.",
            summaryHi = "रेलवे मुख्यालय, अनुच्छेद 17 व 32, गोदावरी व प्रमुख राष्ट्रीय उद्यान।",
            keyFormulas = listOf(
                "ECR HQ: Hajipur | Min age for PM/Lok Sabha: 25 Years",
                "Article 17: Abolition of Untouchability"
            ),
            solvedExampleEn = "Q: Longest river of Peninsular India?\nAns: Godavari (Dakshin Ganga).",
            solvedExampleHi = "प्रश्न: प्रायद्वीपीय भारत की सबसे लंबी नदी?\nउत्तर: गोदावरी।",
            linkedTestIds = listOf("rail_sec_gk", "rail_fl_pyq")
        ),

        // ==================== SSC MATHS BOOSTER ====================
        StudyNoteChapter(
            id = "note_ssc_math_trig", examCategory = "SSC", examClass = "", subject = "Mathematics",
            chapterTitleEn = "Trigonometry & Maxima-Minima Tricks", chapterTitleHi = "त्रिकोणमिति एवं अधिकतम-न्यूनतम मान",
            summaryEn = "Pythagorean identities, sec θ ± tan θ reciprocals, complementary products tan θ·tan(90-θ)=1, and a sinθ + b cosθ bounds.",
            summaryHi = "SSC त्रिकोणमिति: sec±tan व्युत्क्रम, पूरक कोण गुणन व √(a²+b²) अधिकतम मान।",
            keyFormulas = listOf(
                "If sec θ + tan θ = k, then sec θ - tan θ = 1/k",
                "Max value of (a sinθ + b cosθ) = √(a² + b²)",
                "tan A · tan B = 1 when A + B = 90°"
            ),
            solvedExampleEn = "Q: Max value of 3 sinθ + 4 cosθ?\nSol: √(3² + 4²) = 5.",
            solvedExampleHi = "प्रश्न: 3 sinθ + 4 cosθ का अधिकतम मान?\nहल: √(9+16) = 5।",
            linkedTestIds = listOf("ssc_cw_math_trig", "ssc_sw_math", "test_ssc_cgl_chsl_02")
        ),
        StudyNoteChapter(
            id = "note_ssc_math_alg", examCategory = "SSC", examClass = "", subject = "Mathematics",
            chapterTitleEn = "Algebraic Reciprocals & Surds Booster", chapterTitleHi = "बीजगणित, x+1/x व करणी ट्रिक्स",
            summaryEn = "Instant formulas for x²+1/x², x³+1/x³, x⁴+1/x⁴, infinite nested radicals √(n + √(n + ...)), and cyclic unit digit.",
            summaryHi = "x + 1/x से घात 2, 3, 4 निकालने की ट्रिक व अनंत करणी प्रश्न।",
            keyFormulas = listOf(
                "If x + 1/x = k => x² + 1/x² = k² - 2 and x³ + 1/x³ = k³ - 3k",
                "√(a(a+1) + √(a(a+1)+...∞)) = (a + 1)"
            ),
            solvedExampleEn = "Q: If x + 1/x = 5, find x³ + 1/x³.\nSol: 5³ - 3(5) = 110.",
            solvedExampleHi = "प्रश्न: x + 1/x = 5 तो x³ + 1/x³ = ?\nहल: 125 - 15 = 110।",
            linkedTestIds = listOf("ssc_cw_math_alg", "ssc_sec_math")
        ),
        StudyNoteChapter(
            id = "note_ssc_eng_vocab", examCategory = "SSC", examClass = "", subject = "English",
            chapterTitleEn = "SSC Idioms, One Word & Error Rules", chapterTitleHi = "SSC शब्दावली, Idioms व Grammar",
            summaryEn = "High-repeat SSC CGL/CHSL idioms, one-word substitutions (Polyglot, Ornithology), and fixed prepositions.",
            summaryHi = "SSC परीक्षाओं में बार-बार पूछे जाने वाले Idioms, One Word व Prepositions।",
            keyFormulas = listOf(
                "Fond OF | At the eleventh hour = Last moment | Ephemeral = Short-lived",
                "'Each of + Plural Noun' always takes Singular Verb"
            ),
            solvedExampleEn = "Q: One word for 'Study of birds'?\nAns: Ornithology.",
            solvedExampleHi = "प्रश्न: 'At the eleventh hour' का अर्थ?\nउत्तर: अंतिम क्षण में।",
            linkedTestIds = listOf("ssc_cw_eng_idiom", "ssc_sw_eng", "ssc_sec_eng")
        ),
        StudyNoteChapter(
            id = "note_ssc_reas_mix", examCategory = "SSC", examClass = "", subject = "Reasoning / Mental Ability",
            chapterTitleEn = "Polygon Diagonals, Clocks & Analogy", chapterTitleHi = "विकर्ण, दर्पण घड़ी व संख्या सादृश्यता",
            summaryEn = "Diagonals n(n-3)/2, clock mirror reflection (11:60 - Time), and number series multipliers.",
            summaryHi = "बहुभुज में विकर्ण सूत्र, घड़ी दर्पण समय व तार्किक श्रृंखला।",
            keyFormulas = listOf(
                "Number of diagonals in n-gon = n(n - 3) / 2",
                "Mirror time of clock = 11:60 - Given Time"
            ),
            solvedExampleEn = "Q: Mirror image of 8:20?\nSol: 11:60 - 8:20 = 3:40.",
            solvedExampleHi = "प्रश्न: 8:20 का दर्पण समय?\nहल: 11:60 - 8:20 = 3:40।",
            linkedTestIds = listOf("ssc_sec_reas")
        ),
        StudyNoteChapter(
            id = "note_ssc_gk_pol", examCategory = "SSC", examClass = "", subject = "General Knowledge",
            chapterTitleEn = "Polity, Economics & Static GK Capsule", chapterTitleHi = "संविधान, GST व ऐतिहासिक पुस्तकें",
            summaryEn = "Arthashastra (Kautilya), GST rollout (1 July 2017), Article 32 (Heart & Soul), Master Gland (Pituitary), and Mariana Trench.",
            summaryHi = "कौटिल्य अर्थशास्त्र, GST लागू तिथि, अनुच्छेद 32 व पीयूष ग्रंथि।",
            keyFormulas = listOf(
                "GST Implementation: 1 July 2017 | Article 32: Constitutional Remedies",
                "Master Gland: Pituitary | Deepest Trench: Mariana Trench"
            ),
            solvedExampleEn = "Q: Who wrote Arthashastra?\nAns: Kautilya (Chanakya).",
            solvedExampleHi = "प्रश्न: भारत में GST कब लागू हुआ?\nउत्तर: 1 जुलाई 2017।",
            linkedTestIds = listOf("ssc_sec_gk", "ssc_fl_pyq")
        )
    )
}
