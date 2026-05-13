package com.akshara.deepa.data.db

import com.akshara.deepa.data.models.*

object SeedData {

    suspend fun seedAll(cd: ChapterDao, qd: QuestionDao) {
        cd.insertAll(allChapters())
        qd.insertAll(allQuestions())
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private fun ch(id: String, sub: String, m: String, n: Int, orig: String, en: String, pdf: String) =
        Chapter(id = id, subjectId = sub, mediumCode = m, number = n,
                nameOriginal = orig, nameEn = en, pdfFolder = pdf)

    private fun q(id:String, cid:String, sid:String, m:String, qt:String,
                  a:String, b:String, c:String, d:String, ans:String, exp:String="") =
        Question(id=id,chapterId=cid,subjectId=sid,mediumCode=m,questionText=qt,
                 optionA=a,optionB=b,optionC=c,optionD=d,correctOption=ans,explanation=exp)

    // ═══════════════════════════════════════════════════════════════════════════
    // ALL CHAPTERS
    // ═══════════════════════════════════════════════════════════════════════════
    fun allChapters() = scienceEN() + scienceKN() + mathsEN() + mathsKN() +
            socialEN() + socialKN() + eng1EN() + eng2EN() + eng2KN() +
            kan1EN() + kan1KN() + kan2EN() + hindiEN() + hindiKN() + peEN() + peKN()

    // ── SCIENCE English (13 ch) ───────────────────────────────────────────────
    private fun scienceEN() = listOf(
        ch("en_sc_1","en_science","en",1,"Chemical Reactions and Equations","Chemical Reactions and Equations","en/science"),
        ch("en_sc_2","en_science","en",2,"Acids, Bases and Salts","Acids, Bases and Salts","en/science"),
        ch("en_sc_3","en_science","en",3,"Metals and Non-metals","Metals and Non-metals","en/science"),
        ch("en_sc_4","en_science","en",4,"Carbon and its Compounds","Carbon and its Compounds","en/science"),
        ch("en_sc_5","en_science","en",5,"Periodic Classification of Elements","Periodic Classification of Elements","en/science"),
        ch("en_sc_6","en_science","en",6,"Life Processes","Life Processes","en/science"),
        ch("en_sc_7","en_science","en",7,"Control and Coordination","Control and Coordination","en/science"),
        ch("en_sc_8","en_science","en",8,"How do Organisms Reproduce?","How do Organisms Reproduce?","en/science"),
        ch("en_sc_9","en_science","en",9,"Heredity and Evolution","Heredity and Evolution","en/science"),
        ch("en_sc_10","en_science","en",10,"Light – Reflection and Refraction","Light – Reflection and Refraction","en/science"),
        ch("en_sc_11","en_science","en",11,"Human Eye and the Colourful World","Human Eye and the Colourful World","en/science"),
        ch("en_sc_12","en_science","en",12,"Electricity","Electricity","en/science"),
        ch("en_sc_13","en_science","en",13,"Magnetic Effects of Electric Current","Magnetic Effects of Electric Current","en/science")
    )

    // ── SCIENCE Kannada (13 ch) ───────────────────────────────────────────────
    private fun scienceKN() = listOf(
        ch("kn_sc_1","kn_science","kn",1,"ರಾಸಾಯನಿಕ ಕ್ರಿಯೆಗಳು ಮತ್ತು ಸಮೀಕರಣಗಳು","Chemical Reactions and Equations","kn/science"),
        ch("kn_sc_2","kn_science","kn",2,"ಆಮ್ಲಗಳು, ಕ್ಷಾರಗಳು ಮತ್ತು ಲವಣಗಳು","Acids, Bases and Salts","kn/science"),
        ch("kn_sc_3","kn_science","kn",3,"ಲೋಹಗಳು ಮತ್ತು ಅಲೋಹಗಳು","Metals and Non-metals","kn/science"),
        ch("kn_sc_4","kn_science","kn",4,"ಕಾರ್ಬನ್ ಮತ್ತು ಅದರ ಸಂಯುಕ್ತಗಳು","Carbon and its Compounds","kn/science"),
        ch("kn_sc_5","kn_science","kn",5,"ಧಾತುಗಳ ಆವರ್ತ ವರ್ಗೀಕರಣ","Periodic Classification of Elements","kn/science"),
        ch("kn_sc_6","kn_science","kn",6,"ಜೀವ ಪ್ರಕ್ರಿಯೆಗಳು","Life Processes","kn/science"),
        ch("kn_sc_7","kn_science","kn",7,"ನಿಯಂತ್ರಣ ಮತ್ತು ಸಮನ್ವಯ","Control and Coordination","kn/science"),
        ch("kn_sc_8","kn_science","kn",8,"ಜೀವಿಗಳ ಸಂತಾನೋತ್ಪತ್ತಿ","How do Organisms Reproduce?","kn/science"),
        ch("kn_sc_9","kn_science","kn",9,"ಆನುವಂಶಿಕತೆ ಮತ್ತು ವಿಕಾಸ","Heredity and Evolution","kn/science"),
        ch("kn_sc_10","kn_science","kn",10,"ಬೆಳಕು – ಪ್ರತಿಫಲನ ಮತ್ತು ವಕ್ರೀಭವನ","Light – Reflection and Refraction","kn/science"),
        ch("kn_sc_11","kn_science","kn",11,"ಮಾನವ ಕಣ್ಣು ಮತ್ತು ವರ್ಣಮಯ ಜಗತ್ತು","Human Eye and the Colourful World","kn/science"),
        ch("kn_sc_12","kn_science","kn",12,"ವಿದ್ಯುತ್","Electricity","kn/science"),
        ch("kn_sc_13","kn_science","kn",13,"ವಿದ್ಯುತ್ ಪ್ರವಾಹದ ಕಾಂತೀಯ ಪ್ರಭಾವಗಳು","Magnetic Effects of Electric Current","kn/science")
    )

    // ── MATHS English (14 ch) ─────────────────────────────────────────────────
    private fun mathsEN() = listOf(
        ch("en_mt_1","en_maths","en",1,"Real Numbers","Real Numbers","en/maths"),
        ch("en_mt_2","en_maths","en",2,"Polynomials","Polynomials","en/maths"),
        ch("en_mt_3","en_maths","en",3,"Pair of Linear Equations in Two Variables","Pair of Linear Equations in Two Variables","en/maths"),
        ch("en_mt_4","en_maths","en",4,"Quadratic Equations","Quadratic Equations","en/maths"),
        ch("en_mt_5","en_maths","en",5,"Arithmetic Progressions","Arithmetic Progressions","en/maths"),
        ch("en_mt_6","en_maths","en",6,"Triangles","Triangles","en/maths"),
        ch("en_mt_7","en_maths","en",7,"Coordinate Geometry","Coordinate Geometry","en/maths"),
        ch("en_mt_8","en_maths","en",8,"Introduction to Trigonometry","Introduction to Trigonometry","en/maths"),
        ch("en_mt_9","en_maths","en",9,"Some Applications of Trigonometry","Some Applications of Trigonometry","en/maths"),
        ch("en_mt_10","en_maths","en",10,"Circles","Circles","en/maths"),
        ch("en_mt_11","en_maths","en",11,"Areas Related to Circles","Areas Related to Circles","en/maths"),
        ch("en_mt_12","en_maths","en",12,"Surface Areas and Volumes","Surface Areas and Volumes","en/maths"),
        ch("en_mt_13","en_maths","en",13,"Statistics","Statistics","en/maths"),
        ch("en_mt_14","en_maths","en",14,"Probability","Probability","en/maths")
    )

    // ── MATHS Kannada (14 ch) ─────────────────────────────────────────────────
    private fun mathsKN() = listOf(
        ch("kn_mt_1","kn_maths","kn",1,"ನೈಜ ಸಂಖ್ಯೆಗಳು","Real Numbers","kn/maths"),
        ch("kn_mt_2","kn_maths","kn",2,"ಬಹುಪದೀಯಗಳು","Polynomials","kn/maths"),
        ch("kn_mt_3","kn_maths","kn",3,"ಎರಡು ಚರ ರಾಶಿಗಳ ರೇಖಾ ಸಮೀಕರಣ ಜೋಡಿ","Pair of Linear Equations","kn/maths"),
        ch("kn_mt_4","kn_maths","kn",4,"ವರ್ಗ ಸಮೀಕರಣಗಳು","Quadratic Equations","kn/maths"),
        ch("kn_mt_5","kn_maths","kn",5,"ಅಂಕಗಣಿತ ಪ್ರಗತಿ","Arithmetic Progressions","kn/maths"),
        ch("kn_mt_6","kn_maths","kn",6,"ತ್ರಿಭುಜಗಳು","Triangles","kn/maths"),
        ch("kn_mt_7","kn_maths","kn",7,"ನಿರ್ದೇಶಾಂಕ ರೇಖಾಗಣಿತ","Coordinate Geometry","kn/maths"),
        ch("kn_mt_8","kn_maths","kn",8,"ತ್ರಿಕೋಣಮಿತಿ ಪರಿಚಯ","Introduction to Trigonometry","kn/maths"),
        ch("kn_mt_9","kn_maths","kn",9,"ತ್ರಿಕೋಣಮಿತಿಯ ಕೆಲವು ಅನ್ವಯಗಳು","Some Applications of Trigonometry","kn/maths"),
        ch("kn_mt_10","kn_maths","kn",10,"ವೃತ್ತಗಳು","Circles","kn/maths"),
        ch("kn_mt_11","kn_maths","kn",11,"ವೃತ್ತಗಳಿಗೆ ಸಂಬಂಧಿಸಿದ ವಿಸ್ತೀರ್ಣಗಳು","Areas Related to Circles","kn/maths"),
        ch("kn_mt_12","kn_maths","kn",12,"ಮೇಲ್ಮೈ ವಿಸ್ತೀರ್ಣ ಮತ್ತು ಘನಫಲಗಳು","Surface Areas and Volumes","kn/maths"),
        ch("kn_mt_13","kn_maths","kn",13,"ಸಂಖ್ಯಾಶಾಸ್ತ್ರ","Statistics","kn/maths"),
        ch("kn_mt_14","kn_maths","kn",14,"ಸಂಭವನೀಯತೆ","Probability","kn/maths")
    )

    // ── SOCIAL SCIENCE English (32 ch) ───────────────────────────────────────
    private fun socialEN() = listOf(
        // History
        ch("en_ss_1","en_social","en",1,"The Rise of Nationalism in Europe","The Rise of Nationalism in Europe","en/social"),
        ch("en_ss_2","en_social","en",2,"The Nationalist Movement in Indo-China","The Nationalist Movement in Indo-China","en/social"),
        ch("en_ss_3","en_social","en",3,"Nationalism in India","Nationalism in India","en/social"),
        ch("en_ss_4","en_social","en",4,"The Age of Industrialisation","The Age of Industrialisation","en/social"),
        ch("en_ss_5","en_social","en",5,"Print Culture and the Modern World","Print Culture and the Modern World","en/social"),
        ch("en_ss_6","en_social","en",6,"Work, Life and Leisure","Work, Life and Leisure","en/social"),
        ch("en_ss_7","en_social","en",7,"Peasants and Farmers","Peasants and Farmers","en/social"),
        ch("en_ss_8","en_social","en",8,"History and Novel","History and Novel","en/social"),
        // Geography
        ch("en_ss_9","en_social","en",9,"Resources and Development","Resources and Development","en/social"),
        ch("en_ss_10","en_social","en",10,"Forest and Wildlife Resources","Forest and Wildlife Resources","en/social"),
        ch("en_ss_11","en_social","en",11,"Water Resources","Water Resources","en/social"),
        ch("en_ss_12","en_social","en",12,"Agriculture","Agriculture","en/social"),
        ch("en_ss_13","en_social","en",13,"Minerals and Energy Resources","Minerals and Energy Resources","en/social"),
        ch("en_ss_14","en_social","en",14,"Manufacturing Industries","Manufacturing Industries","en/social"),
        ch("en_ss_15","en_social","en",15,"Life Lines of National Economy","Life Lines of National Economy","en/social"),
        // Political Science
        ch("en_ss_16","en_social","en",16,"Power Sharing","Power Sharing","en/social"),
        ch("en_ss_17","en_social","en",17,"Federalism","Federalism","en/social"),
        ch("en_ss_18","en_social","en",18,"Democracy and Diversity","Democracy and Diversity","en/social"),
        ch("en_ss_19","en_social","en",19,"Gender, Religion and Caste","Gender, Religion and Caste","en/social"),
        ch("en_ss_20","en_social","en",20,"Popular Struggles and Movements","Popular Struggles and Movements","en/social"),
        ch("en_ss_21","en_social","en",21,"Political Parties","Political Parties","en/social"),
        ch("en_ss_22","en_social","en",22,"Outcomes of Democracy","Outcomes of Democracy","en/social"),
        ch("en_ss_23","en_social","en",23,"Challenges to Democracy","Challenges to Democracy","en/social"),
        // Economics
        ch("en_ss_24","en_social","en",24,"Development","Development","en/social"),
        ch("en_ss_25","en_social","en",25,"Sectors of the Indian Economy","Sectors of the Indian Economy","en/social"),
        ch("en_ss_26","en_social","en",26,"Money and Credit","Money and Credit","en/social"),
        ch("en_ss_27","en_social","en",27,"Globalisation and the Indian Economy","Globalisation and the Indian Economy","en/social"),
        ch("en_ss_28","en_social","en",28,"Consumer Rights","Consumer Rights","en/social"),
        // Disaster Management
        ch("en_ss_29","en_social","en",29,"Natural Disasters","Natural Disasters","en/social"),
        ch("en_ss_30","en_social","en",30,"Disaster Management","Disaster Management","en/social"),
        ch("en_ss_31","en_social","en",31,"First Aid and Safety","First Aid and Safety","en/social"),
        ch("en_ss_32","en_social","en",32,"Community Safety and Response","Community Safety and Response","en/social")
    )

    // ── SOCIAL SCIENCE Kannada (33 ch) ────────────────────────────────────────
    private fun socialKN() = listOf(
        ch("kn_ss_1","kn_social","kn",1,"ಯೂರೋಪ್‌ನಲ್ಲಿ ರಾಷ್ಟ್ರೀಯತೆಯ ಉದಯ","Rise of Nationalism in Europe","kn/social"),
        ch("kn_ss_2","kn_social","kn",2,"ಇಂಡೋ-ಚೀನಾದಲ್ಲಿ ರಾಷ್ಟ್ರೀಯ ಚಳವಳಿ","Nationalist Movement in Indo-China","kn/social"),
        ch("kn_ss_3","kn_social","kn",3,"ಭಾರತದಲ್ಲಿ ರಾಷ್ಟ್ರೀಯತೆ","Nationalism in India","kn/social"),
        ch("kn_ss_4","kn_social","kn",4,"ಕೈಗಾರಿಕೀಕರಣದ ಯುಗ","The Age of Industrialisation","kn/social"),
        ch("kn_ss_5","kn_social","kn",5,"ಮುದ್ರಣ ಸಂಸ್ಕೃತಿ ಮತ್ತು ಆಧುನಿಕ ಜಗತ್ತು","Print Culture and Modern World","kn/social"),
        ch("kn_ss_6","kn_social","kn",6,"ಕೃಷಿ ಮತ್ತು ರೈತರು","Peasants and Farmers","kn/social"),
        ch("kn_ss_7","kn_social","kn",7,"ಸಂಪನ್ಮೂಲಗಳು ಮತ್ತು ಅಭಿವೃದ್ಧಿ","Resources and Development","kn/social"),
        ch("kn_ss_8","kn_social","kn",8,"ಅರಣ್ಯ ಮತ್ತು ವನ್ಯಜೀವಿ ಸಂಪನ್ಮೂಲ","Forest and Wildlife Resources","kn/social"),
        ch("kn_ss_9","kn_social","kn",9,"ಜಲ ಸಂಪನ್ಮೂಲ","Water Resources","kn/social"),
        ch("kn_ss_10","kn_social","kn",10,"ಕೃಷಿ","Agriculture","kn/social"),
        ch("kn_ss_11","kn_social","kn",11,"ಖನಿಜ ಮತ್ತು ಶಕ್ತಿ ಸಂಪನ್ಮೂಲ","Minerals and Energy Resources","kn/social"),
        ch("kn_ss_12","kn_social","kn",12,"ಉತ್ಪಾದನಾ ಕೈಗಾರಿಕೆಗಳು","Manufacturing Industries","kn/social"),
        ch("kn_ss_13","kn_social","kn",13,"ರಾಷ್ಟ್ರೀಯ ಆರ್ಥಿಕತೆಯ ಜೀವನಾಡಿಗಳು","Life Lines of National Economy","kn/social"),
        ch("kn_ss_14","kn_social","kn",14,"ಅಧಿಕಾರ ಹಂಚಿಕೆ","Power Sharing","kn/social"),
        ch("kn_ss_15","kn_social","kn",15,"ಒಕ್ಕೂಟ ವ್ಯವಸ್ಥೆ","Federalism","kn/social"),
        ch("kn_ss_16","kn_social","kn",16,"ಪ್ರಜಾಪ್ರಭುತ್ವ ಮತ್ತು ವೈವಿಧ್ಯ","Democracy and Diversity","kn/social"),
        ch("kn_ss_17","kn_social","kn",17,"ಲಿಂಗ, ಧರ್ಮ ಮತ್ತು ಜಾತಿ","Gender, Religion and Caste","kn/social"),
        ch("kn_ss_18","kn_social","kn",18,"ಜನಪ್ರಿಯ ಹೋರಾಟಗಳು ಮತ್ತು ಆಂದೋಲನಗಳು","Popular Struggles and Movements","kn/social"),
        ch("kn_ss_19","kn_social","kn",19,"ರಾಜಕೀಯ ಪಕ್ಷಗಳು","Political Parties","kn/social"),
        ch("kn_ss_20","kn_social","kn",20,"ಪ್ರಜಾಪ್ರಭುತ್ವದ ಫಲಿತಾಂಶಗಳು","Outcomes of Democracy","kn/social"),
        ch("kn_ss_21","kn_social","kn",21,"ಅಭಿವೃದ್ಧಿ","Development","kn/social"),
        ch("kn_ss_22","kn_social","kn",22,"ಭಾರತೀಯ ಆರ್ಥಿಕತೆಯ ವಲಯಗಳು","Sectors of the Indian Economy","kn/social"),
        ch("kn_ss_23","kn_social","kn",23,"ಹಣ ಮತ್ತು ಸಾಲ","Money and Credit","kn/social"),
        ch("kn_ss_24","kn_social","kn",24,"ಜಾಗತೀಕರಣ ಮತ್ತು ಭಾರತೀಯ ಆರ್ಥಿಕತೆ","Globalisation and Indian Economy","kn/social"),
        ch("kn_ss_25","kn_social","kn",25,"ಗ್ರಾಹಕ ಹಕ್ಕುಗಳು","Consumer Rights","kn/social"),
        ch("kn_ss_26","kn_social","kn",26,"ನೈಸರ್ಗಿಕ ವಿಕೋಪಗಳು","Natural Disasters","kn/social"),
        ch("kn_ss_27","kn_social","kn",27,"ವಿಪತ್ತು ನಿರ್ವಹಣೆ","Disaster Management","kn/social"),
        ch("kn_ss_28","kn_social","kn",28,"ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ","First Aid","kn/social"),
        ch("kn_ss_29","kn_social","kn",29,"ಸಮುದಾಯ ಸುರಕ್ಷತೆ","Community Safety","kn/social"),
        ch("kn_ss_30","kn_social","kn",30,"ಕರ್ನಾಟಕ – ಭೂಗೋಳ","Karnataka Geography","kn/social"),
        ch("kn_ss_31","kn_social","kn",31,"ಕರ್ನಾಟಕ – ಇತಿಹಾಸ","Karnataka History","kn/social"),
        ch("kn_ss_32","kn_social","kn",32,"ಕರ್ನಾಟಕ – ಆರ್ಥಿಕತೆ","Karnataka Economy","kn/social"),
        ch("kn_ss_33","kn_social","kn",33,"ಕರ್ನಾಟಕ – ರಾಜ್ಯ ಸರ್ಕಾರ","Karnataka State Government","kn/social")
    )

    // ── ENGLISH 1ST LANGUAGE (23 ch) ─────────────────────────────────────────
    private fun eng1EN() = listOf(
        ch("en_e1_1","en_english1","en",1,"A Letter to God","A Letter to God","en/english1"),
        ch("en_e1_2","en_english1","en",2,"Nelson Mandela: Long Walk to Freedom","Nelson Mandela: Long Walk to Freedom","en/english1"),
        ch("en_e1_3","en_english1","en",3,"Two Stories about Flying","Two Stories about Flying","en/english1"),
        ch("en_e1_4","en_english1","en",4,"From the Diary of Anne Frank","From the Diary of Anne Frank","en/english1"),
        ch("en_e1_5","en_english1","en",5,"The Hundred Dresses – I","The Hundred Dresses – I","en/english1"),
        ch("en_e1_6","en_english1","en",6,"The Hundred Dresses – II","The Hundred Dresses – II","en/english1"),
        ch("en_e1_7","en_english1","en",7,"Glimpses of India","Glimpses of India","en/english1"),
        ch("en_e1_8","en_english1","en",8,"Mijbil the Otter","Mijbil the Otter","en/english1"),
        ch("en_e1_9","en_english1","en",9,"Madam Rides the Bus","Madam Rides the Bus","en/english1"),
        ch("en_e1_10","en_english1","en",10,"The Sermon at Benares","The Sermon at Benares","en/english1"),
        ch("en_e1_11","en_english1","en",11,"The Proposal","The Proposal","en/english1"),
        ch("en_e1_12","en_english1","en",12,"Dust of Snow","Dust of Snow (Poetry)","en/english1"),
        ch("en_e1_13","en_english1","en",13,"Fire and Ice","Fire and Ice (Poetry)","en/english1"),
        ch("en_e1_14","en_english1","en",14,"A Tiger in the Zoo","A Tiger in the Zoo (Poetry)","en/english1"),
        ch("en_e1_15","en_english1","en",15,"How to Tell Wild Animals","How to Tell Wild Animals (Poetry)","en/english1"),
        ch("en_e1_16","en_english1","en",16,"The Ball Poem","The Ball Poem (Poetry)","en/english1"),
        ch("en_e1_17","en_english1","en",17,"Amanda!","Amanda! (Poetry)","en/english1"),
        ch("en_e1_18","en_english1","en",18,"Animals","Animals (Poetry)","en/english1"),
        ch("en_e1_19","en_english1","en",19,"The Trees","The Trees (Poetry)","en/english1"),
        ch("en_e1_20","en_english1","en",20,"Fog","Fog (Poetry)","en/english1"),
        ch("en_e1_21","en_english1","en",21,"The Tale of Custard the Dragon","The Tale of Custard the Dragon","en/english1"),
        ch("en_e1_22","en_english1","en",22,"For Anne Gregory","For Anne Gregory (Poetry)","en/english1"),
        ch("en_e1_23","en_english1","en",23,"The Thief's Story","The Thief's Story","en/english1")
    )

    // ── ENGLISH 2ND LANGUAGE (20 ch) EN + KN ─────────────────────────────────
    private fun eng2EN() = (1..20).map { n ->
        val title = when(n) {
            1->"The Little Girl"; 2->"Windmill"; 3->"What is My Name?"; 4->"Not Just a Teacher";
            5->"School of Excellence"; 6->"Sea of Stories"; 7->"The Power of Music"; 8->"A Lamp in the Dark";
            9->"Grandma Climbs a Tree"; 10->"Around the World"; 11->"The Doctor's Word"; 12->"Factory Boys";
            13->"Colours"; 14->"A Traveller's Tale"; 15->"On the Road"; 16->"Bamboo Curry";
            17->"The Journey Home"; 18->"The Best Christmas Present"; 19->"Grammar and Composition"; 20->"Writing Skills"
            else -> "Chapter $n"
        }
        ch("en_e2_$n","en_english2","en",n,title,title,"en/english2")
    }

    private fun eng2KN() = (1..20).map { n ->
        val title = when(n) {
            1->"The Little Girl"; 2->"Windmill"; 3->"What is My Name?"; 4->"Not Just a Teacher";
            5->"School of Excellence"; 6->"Sea of Stories"; 7->"The Power of Music"; 8->"A Lamp in the Dark";
            9->"Grandma Climbs a Tree"; 10->"Around the World"; 11->"The Doctor's Word"; 12->"Factory Boys";
            13->"Colours"; 14->"A Traveller's Tale"; 15->"On the Road"; 16->"Bamboo Curry";
            17->"The Journey Home"; 18->"The Best Christmas Present"; 19->"Grammar and Composition"; 20->"Writing Skills"
            else -> "Chapter $n"
        }
        ch("kn_e2_$n","kn_english2","kn",n,title,title,"kn/english2")
    }

    // ── KANNADA 1ST LANGUAGE (22 ch) ─────────────────────────────────────────
    private val kannadaChapterNames1 = listOf(
        "ಮಳೆ", "ಕೋಗಿಲೆ ಹಾಡಿದಳು", "ಶ್ರೀಮತಿ ಟೀಚರ್", "ಪ್ರತಿಭಾ", "ಆನೆ ನಡೆದ ದಾರಿ",
        "ಕಾಡಿನ ಮಧ್ಯೆ", "ವಿದ್ಯಾರ್ಥಿ ಲೋಕ", "ಬದುಕು ಜಲತರಂಗ", "ನಮ್ಮ ಊರಿನ ಬೆಳಕು",
        "ಗಾಂಧೀಜಿ", "ಕನ್ನಡ ನಾಡು", "ಭಾರತ ಭೂಮಿ", "ಸ್ನೇಹ", "ವಿಜ್ಞಾನದ ದಾರಿ",
        "ಸಂಧಿ ಮತ್ತು ಸಮಾಸ", "ಅಲಂಕಾರ", "ವ್ಯಾಕರಣ", "ಪ್ರಬಂಧ ಲೇಖನ",
        "ಪತ್ರ ಲೇಖನ", "ಸಂಕ್ಷೇಪ ಲೇಖನ", "ಭಾಷಾ ಕೌಶಲ", "ಕನ್ನಡ ಸಾಹಿತ್ಯ"
    )

    private fun kan1EN() = kannadaChapterNames1.mapIndexed { i, name ->
        ch("en_k1_${i+1}","en_kannada1","en",i+1,name,name,"en/kannada1")
    }
    private fun kan1KN() = kannadaChapterNames1.mapIndexed { i, name ->
        ch("kn_k1_${i+1}","kn_kannada1","kn",i+1,name,name,"kn/kannada1")
    }

    // ── KANNADA 2ND LANGUAGE EN only (19 ch) ──────────────────────────────────
    private val kannadaChapterNames2 = listOf(
        "ನಮ್ಮ ಭಾಷೆ", "ಮಾತು ಮಾಡಿ ಮನ ಗೆಲ್ಲಿ", "ಪ್ರಕೃತಿ ಸೌಂದರ್ಯ", "ಶಾಲೆ ಮತ್ತು ಜೀವನ",
        "ಕನ್ನಡ ಸಂಸ್ಕೃತಿ", "ನಾಡಿನ ಹೆಮ್ಮೆ", "ಜ್ಞಾನದ ಬೆಳಕು", "ಮಾನವ ಸಂಬಂಧ",
        "ಪ್ರವಾಸ ಕಥನ", "ಲೋಕೋಕ್ತಿ ಮತ್ತು ನಾಣ್ಣುಡಿ", "ಕನ್ನಡ ವ್ಯಾಕರಣ",
        "ಸಂಧಿ ನಿಯಮ", "ಕ್ರಿಯಾಪದ", "ಸರ್ವನಾಮ", "ವಿಶೇಷಣ",
        "ಪ್ರಬಂಧ", "ಪತ್ರ", "ಸಂವಾದ", "ಅನುವಾದ"
    )
    private fun kan2EN() = kannadaChapterNames2.mapIndexed { i, name ->
        ch("en_k2_${i+1}","en_kannada2","en",i+1,name,name,"en/kannada2")
    }

    // ── HINDI (19 ch) EN + KN ─────────────────────────────────────────────────
    private val hindiChapterNames = listOf(
        "सूरदास के पद", "राम-लक्ष्मण-परशुराम संवाद", "देव के सवैये",
        "आत्मकथ्य", "उत्साह और अट नहीं रही है", "यह दंतुरहित मुस्कान",
        "छाया मत छूना", "कन्यादान", "संगतकार", "नेताजी का चश्मा",
        "बालगोबिन भगत", "लखनवी अंदाज़", "मानवीय करुणा की दिव्य चमक",
        "एक कहानी यह भी", "स्त्री शिक्षा के विरोधी कुतर्कों का खंडन",
        "नौबतखाने में इबादत", "संस्कृति", "व्याकरण", "लेखन कौशल"
    )
    private fun hindiEN() = hindiChapterNames.mapIndexed { i, name ->
        ch("en_hi_${i+1}","en_hindi","en",i+1,name,name,"en/hindi")
    }
    private fun hindiKN() = hindiChapterNames.mapIndexed { i, name ->
        ch("kn_hi_${i+1}","kn_hindi","kn",i+1,name,name,"kn/hindi")
    }

    // ── PHYSICAL EDUCATION (30 ch) EN + KN ───────────────────────────────────
    private val peChapterNames = listOf(
        "Modern Olympics and Asian Games", "Volley Ball", "Volleyball",
        "Hockey", "Hockey", "Athletics", "Hurdles Race", "Walking Race",
        "Walking Race", "Aerobics", "Community Health", "Rhythmic Activities",
        "First Aid", "Lifestyle Diseases", "Drill and Marching", "Hand Ball",
        "Handball", "Basket Ball", "Basket Ball", "Badminton", "Badminton",
        "Discus Throw", "Discus Throw", "Meditation", "Yogasana",
        "Communicable Diseases", "Self Defence Techniques", "National Integration",
        "National Integration", "Recreational Games"
    )
    private fun peEN() = peChapterNames.mapIndexed { i, name ->
        val kn = when(i) {
            0->"ದೈಹಿಕ ಸದೃಢತೆ"; 1->"ಶರೀರ ರಚನೆ"; 2->"ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ"; 3->"ಯೋಗ ಮತ್ತು ಧ್ಯಾನ"
            else -> name
        }
        ch("en_pe_${i+1}","en_pe","en",i+1,name,name,"en/pe")
    }
    private fun peKN() = peChapterNames.mapIndexed { i, name ->
        val kn = when(i) {
            0->"ದೈಹಿಕ ಸದೃಢತೆ"; 1->"ಶರೀರ ರಚನೆ"; 2->"ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ"; 3->"ಯೋಗ ಮತ್ತು ಧ್ಯಾನ"
            else -> name
        }
        ch("kn_pe_${i+1}","kn_pe","kn",i+1,if(i<4) kn else name, name,"kn/pe")
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // QUESTIONS (10–15 per core chapter)
    // ═══════════════════════════════════════════════════════════════════════════
    fun allQuestions(): List<Question> {
        val qs = mutableListOf<Question>()
        qs.addAll(scienceEnQ()); qs.addAll(scienceKnQ())
        qs.addAll(mathsEnQ());   qs.addAll(mathsKnQ())
        qs.addAll(socialEnQ());  qs.addAll(socialKnQ())
        qs.addAll(eng1EnQ());    qs.addAll(eng2EnQ())
        qs.addAll(kannadaQ());   qs.addAll(hindiQ())
        return qs
    }

    private fun scienceEnQ() = listOf(
        q("es1_1","en_sc_1","en_science","en","Which law states mass is conserved in chemical reactions?","Newton's Law","Law of Conservation of Mass","Avogadro's Law","Boyle's Law","B","Mass neither created nor destroyed."),
        q("es1_2","en_sc_1","en_science","en","CaCO₃ → CaO + CO₂ is which type of reaction?","Combination","Displacement","Decomposition","Oxidation","C","One compound breaks into two — decomposition."),
        q("es1_3","en_sc_1","en_science","en","The reaction Fe + CuSO₄ → FeSO₄ + Cu is:","Combination","Single Displacement","Double Displacement","Decomposition","B","Iron displaces copper from copper sulphate."),
        q("es1_4","en_sc_1","en_science","en","Gaining of oxygen is called:","Reduction","Oxidation","Combination","Displacement","B","Oxidation = gaining oxygen or losing electrons."),
        q("es1_5","en_sc_1","en_science","en","Which reaction releases heat and light?","Endothermic","Exothermic","Displacement","Neutralisation","B","Exothermic reactions release energy."),
        q("es1_6","en_sc_1","en_science","en","2H₂ + O₂ → 2H₂O is a:","Decomposition","Combination","Redox","Displacement","B","Two substances combine to form one product."),
        q("es1_7","en_sc_1","en_science","en","AgNO₃ + NaCl → AgCl + NaNO₃ is:","Single displacement","Combination","Double displacement","Decomposition","C","Two compounds exchange ions."),
        q("es1_8","en_sc_1","en_science","en","Which prevents rusting of iron?","Galvanisation","Alloying","Reduction","Combination","A","Zinc coating prevents rusting — galvanisation."),
        q("es1_9","en_sc_1","en_science","en","Food becoming rancid is caused by:","Hydrolysis","Reduction","Oxidation","Decomposition","C","Oxidation of fats causes rancidity."),
        q("es1_10","en_sc_1","en_science","en","Thermite reaction is which type?","Combination","Decomposition","Displacement","Precipitation","C","Al displaces Fe from Fe₂O₃."),
        q("es2_1","en_sc_2","en_science","en","pH of neutral solution is:","0","7","14","1","B","pH 7 = neutral; <7 = acidic; >7 = basic."),
        q("es2_2","en_sc_2","en_science","en","NaCl is the formula of:","Baking soda","Common salt","Washing soda","Plaster of Paris","B","NaCl = Sodium Chloride = Common salt."),
        q("es2_3","en_sc_2","en_science","en","Acid turns blue litmus:","Blue","Red","Green","Yellow","B","Acids turn blue litmus red."),
        q("es2_4","en_sc_2","en_science","en","Baking soda chemical name:","Sodium hydroxide","Sodium carbonate","Sodium hydrogen carbonate","Calcium hydroxide","C","NaHCO₃ = sodium hydrogen carbonate."),
        q("es2_5","en_sc_2","en_science","en","Acid + Base → ?","Salt + Water","Only Water","Only Salt","Gas + Water","A","Neutralisation: Acid + Base → Salt + Water."),
        q("es2_6","en_sc_2","en_science","en","Plaster of Paris is obtained from:","Marble","Limestone","Gypsum","Calcite","C","POP = CaSO₄·½H₂O from gypsum."),
        q("es2_7","en_sc_2","en_science","en","Washing soda formula:","Na₂CO₃·10H₂O","NaHCO₃","NaOH","Ca(OH)₂","A","Washing soda = sodium carbonate decahydrate."),
        q("es2_8","en_sc_2","en_science","en","Metal + Acid produces:","Oxygen","Hydrogen","Nitrogen","Carbon dioxide","B","Metal + Acid → Salt + H₂ gas."),
        q("es2_9","en_sc_2","en_science","en","Phenolphthalein turns pink in:","Acid","Neutral","Base","Salt","C","Phenolphthalein is pink/red in alkaline solution."),
        q("es2_10","en_sc_2","en_science","en","Bleaching powder is used for:","Food preservation","Water purification","Glass making","Steel making","B","Bleaching powder purifies water."),
        q("es11_1","en_sc_11","en_science","en","SI unit of electric charge is:","Ampere","Volt","Coulomb","Ohm","C","Electric charge measured in Coulombs (C)."),
        q("es11_2","en_sc_11","en_science","en","Ohm's law states V = ?","I/R","IR","I+R","I²R","B","V = IR (Ohm's law)."),
        q("es11_3","en_sc_11","en_science","en","In series circuit, total resistance is:","Sum of all R","Product of R","Same as each","Reciprocal sum","A","Series: R_total = R₁ + R₂ + ..."),
        q("es11_4","en_sc_11","en_science","en","Electric power P = ?","V/I","VI","V+I","V²","B","Power P = VI = I²R = V²/R."),
        q("es11_5","en_sc_11","en_science","en","1 kWh equals:","1000 W","3.6×10⁶ J","1 J","360 J","B","1 kWh = 3.6×10⁶ joules."),
        q("es11_6","en_sc_11","en_science","en","Joule's heating law: H = ?","I²Rt","IRt","I²R/t","IRt²","A","H = I²Rt (Joule's law of heating)."),
        q("es11_7","en_sc_11","en_science","en","In parallel circuit, voltage across each branch:","Different","Zero","Same","Doubles","C","Voltage same across all branches in parallel."),
        q("es11_8","en_sc_11","en_science","en","Fuse wire has:","High melting point","Low melting point","High conductivity","Low density","B","Low melting point breaks circuit on overload."),
        q("es11_9","en_sc_11","en_science","en","Resistance depends on:","Color","Length, area, material","Weight","Temperature only","B","R = ρL/A."),
        q("es11_10","en_sc_11","en_science","en","Electrons carry which charge?","Positive","Negative","Zero","Variable","B","Electrons carry -ve charge (-1.6×10⁻¹⁹ C).")
    )

    private fun scienceKnQ() = listOf(
        q("ks1_1","kn_sc_1","kn_science","kn","ರಾಸಾಯನಿಕ ಕ್ರಿಯೆಯಲ್ಲಿ ಸಂರಕ್ಷಿತವಾಗುವ ನಿಯಮ?","ನ್ಯೂಟನ್ ನಿಯಮ","ದ್ರವ್ಯರಾಶಿ ಸಂರಕ್ಷಣಾ ನಿಯಮ","ಅವೊಗ್ಯಾಡ್ರೊ ನಿಯಮ","ಬಾಯ್ಲ್ ನಿಯಮ","B","ದ್ರವ್ಯರಾಶಿ ಸೃಷ್ಟಿ ಅಥವಾ ನಾಶ ಆಗುವುದಿಲ್ಲ."),
        q("ks1_2","kn_sc_1","kn_science","kn","CaCO₃ → CaO + CO₂ ಯಾವ ಕ್ರಿಯೆ?","ಸಂಯೋಜನ","ಸ್ಥಾನಾಂತರ","ವಿಘಟನ","ಆಕ್ಸಿಡೀಕರಣ","C","ಒಂದು ವಸ್ತು ಎರಡಾಗುವುದು ವಿಘಟನ ಕ್ರಿಯೆ."),
        q("ks1_3","kn_sc_1","kn_science","kn","ಆಕ್ಸಿಜನ್ ಪಡೆಯುವ ಪ್ರಕ್ರಿಯೆ:","ಅಪಚಯ","ಆಕ್ಸಿಡೀಕರಣ","ಸಂಯೋಜನ","ಸ್ಥಾನಾಂತರ","B","ಆಕ್ಸಿಜನ್ ಗಳಿಸುವ ಪ್ರಕ್ರಿಯೆ ಆಕ್ಸಿಡೀಕರಣ."),
        q("ks1_4","kn_sc_1","kn_science","kn","ಆಹಾರ ಹಳಸುವುದಕ್ಕೆ ಕಾರಣ:","ಜಲಾಪಘಟನ","ಅಪಚಯ","ಆಕ್ಸಿಡೀಕರಣ","ಸ್ಥಾನಾಂತರ","C","ಕೊಬ್ಬು ಆಕ್ಸಿಡೀಕರಣಗೊಂಡಾಗ ಆಹಾರ ಹಳಸುತ್ತದೆ."),
        q("ks1_5","kn_sc_1","kn_science","kn","2H₂ + O₂ → 2H₂O ಇದು:","ವಿಘಟನ","ಸಂಯೋಜನ","ರೆಡಾಕ್ಸ್","ಸ್ಥಾನಾಂತರ","B","ಎರಡು ವಸ್ತು ಸೇರಿ ಒಂದಾಗುವುದು ಸಂಯೋಜನ."),
        q("ks2_1","kn_sc_2","kn_science","kn","ತಟಸ್ಥ ದ್ರಾವಣದ pH ಮೌಲ್ಯ:","0","7","14","1","B","pH 7 = ತಟಸ್ಥ."),
        q("ks2_2","kn_sc_2","kn_science","kn","ಸಾಮಾನ್ಯ ಉಪ್ಪಿನ ಸೂತ್ರ:","NaOH","NaCl","Na₂CO₃","NaHCO₃","B","NaCl = ಸೋಡಿಯಂ ಕ್ಲೋರೈಡ್ = ಸಾಮಾನ್ಯ ಉಪ್ಪು."),
        q("ks2_3","kn_sc_2","kn_science","kn","ಆಮ್ಲ ನೀಲಿ ಲಿಟ್ಮಸ್ ಅನ್ನು ಯಾವ ಬಣ್ಣಕ್ಕೆ ಬದಲಿಸುತ್ತದೆ?","ನೀಲಿ","ಕೆಂಪು","ಹಸಿರು","ಹಳದಿ","B","ಆಮ್ಲಗಳು ನೀಲಿ ಲಿಟ್ಮಸ್ ಅನ್ನು ಕೆಂಪಾಗಿಸುತ್ತವೆ."),
        q("ks11_1","kn_sc_11","kn_science","kn","ವಿದ್ಯುತ್ ಚಾರ್ಜ್‌ನ SI ಘಟಕ:","ಆಂಪಿಯರ್","ವೋಲ್ಟ್","ಕೂಲಂಬ್","ಓಮ್","C","ವಿದ್ಯುತ್ ಚಾರ್ಜ್ ಕೂಲಂಬ್‌ನಲ್ಲಿ ಅಳೆಯಲಾಗುತ್ತದೆ."),
        q("ks11_2","kn_sc_11","kn_science","kn","ಓಮ್ ನಿಯಮ: V = ?","I/R","IR","I+R","I²R","B","V = IR ಓಮ್ ನಿಯಮ."),
        q("ks11_3","kn_sc_11","kn_science","kn","ಸರಣಿ ಸಂಪರ್ಕದಲ್ಲಿ ಒಟ್ಟು ಪ್ರತಿರೋಧ:","ಎಲ್ಲ R ಮೊತ್ತ","R ಗುಣಲಬ್ಧ","ಒಂದೇ R","ಶೂನ್ಯ","A","ಸರಣಿ: R_total = R₁ + R₂ + ..."),
        q("ks11_4","kn_sc_11","kn_science","kn","ವಿದ್ಯುತ್ ಶಕ್ತಿ P = ?","V/I","VI","V+I","V²","B","P = VI = I²R = V²/R."),
        q("ks11_5","kn_sc_11","kn_science","kn","1 kWh ಎಂದರೆ:","1000 W","3.6×10⁶ J","1 J","360 J","B","1 kWh = 3.6×10⁶ ಜೂಲ್.")
    )

    private fun mathsEnQ() = listOf(
        q("em1_1","en_mt_1","en_maths","en","Euclid's division lemma: a=bq+r, then r satisfies:","0<r<b","0≤r<b","0≤r≤b","r>b","B","Remainder r: 0 ≤ r < b."),
        q("em1_2","en_mt_1","en_maths","en","√2 is:","Rational","Irrational","Integer","Natural","B","√2 cannot be written as p/q — irrational."),
        q("em1_3","en_mt_1","en_maths","en","HCF of 6 and 20:","60","2","6","20","B","6=2×3; 20=2²×5; HCF=2."),
        q("em1_4","en_mt_1","en_maths","en","LCM × HCF = ?","Sum of numbers","Product of numbers","Difference","Square","B","LCM × HCF = Product of two numbers."),
        q("em1_5","en_mt_1","en_maths","en","Decimal expansion of 7/8 is:","Terminating","Non-terminating repeating","Non-terminating non-repeating","Infinite","A","Denominator 8=2³, only factor 2 → terminates."),
        q("em1_6","en_mt_1","en_maths","en","π (pi) is:","Rational","Irrational","Integer","Fraction","B","π is non-terminating non-repeating → irrational."),
        q("em1_7","en_mt_1","en_maths","en","Prime factorisation of 120:","2³×3×5","2²×3×5","2³×15","4×30","A","120 = 8×15 = 2³×3×5."),
        q("em1_8","en_mt_1","en_maths","en","HCF of two consecutive integers is always:","2","0","1","The smaller","C","Consecutive integers share no factor other than 1."),
        q("em1_9","en_mt_1","en_maths","en","Every composite number can be expressed as:","Sum of primes","Product of primes","Difference of primes","Square of a prime","B","Fundamental theorem of arithmetic."),
        q("em1_10","en_mt_1","en_maths","en","log₂8 = ?","2","3","4","8","B","2³=8, so log₂8=3."),
        q("em4_1","en_mt_4","en_maths","en","Discriminant of ax²+bx+c=0 is:","b²+4ac","b²-4ac","2b-4ac","b-4ac","B","D = b²-4ac."),
        q("em4_2","en_mt_4","en_maths","en","If D=0, the quadratic has:","No real roots","Two distinct real roots","Two equal real roots","Complex roots","C","D=0 → two equal (repeated) real roots."),
        q("em4_3","en_mt_4","en_maths","en","Sum of roots of ax²+bx+c=0 is:","c/a","b/a","-b/a","-c/a","C","Sum of roots = -b/a."),
        q("em4_4","en_mt_4","en_maths","en","Product of roots of ax²+bx+c=0 is:","c/a","b/a","-b/a","-c/a","A","Product of roots = c/a."),
        q("em4_5","en_mt_4","en_maths","en","Quadratic formula x = ?","-b±√D/a","-b±√D/2a","b±√D/2a","-b±D/2a","B","x = (-b ± √(b²-4ac)) / 2a."),
        q("em8_1","en_mt_8","en_maths","en","sin²θ + cos²θ = ?","0","2","1","θ","C","Pythagorean identity: sin²θ + cos²θ = 1."),
        q("em8_2","en_mt_8","en_maths","en","tan 45° = ?","0","1","√3","1/√3","B","tan 45° = 1."),
        q("em8_3","en_mt_8","en_maths","en","sin 30° = ?","1","1/2","√3/2","0","B","sin 30° = 1/2."),
        q("em8_4","en_mt_8","en_maths","en","cos 0° = ?","0","1","1/2","√3/2","B","cos 0° = 1."),
        q("em8_5","en_mt_8","en_maths","en","1 + tan²θ = ?","sec²θ","cosec²θ","cot²θ","1","A","Identity: 1 + tan²θ = sec²θ."),
        q("em8_6","en_mt_8","en_maths","en","sin 90° = ?","0","1/2","1","√3/2","C","sin 90° = 1."),
        q("em8_7","en_mt_8","en_maths","en","tan θ = ?","sinθ × cosθ","sinθ / cosθ","cosθ / sinθ","1/sinθ","B","tanθ = sinθ/cosθ."),
        q("em8_8","en_mt_8","en_maths","en","sin(90°-θ) = ?","sinθ","cosθ","tanθ","cotθ","B","Complementary identity: sin(90°-θ) = cosθ."),
        q("em14_1","en_mt_14","en_maths","en","Mean of 2,4,6,8,10 is:","4","5","6","7","C","Sum=30, n=5, Mean=6."),
        q("em14_2","en_mt_14","en_maths","en","The middle value of ordered data is:","Mean","Median","Mode","Range","B","Median is the middle value when data is ordered."),
        q("em14_3","en_mt_14","en_maths","en","Most frequently occurring value in data is:","Mean","Median","Mode","Variance","C","Mode = most frequent value."),
        q("em14_4","en_mt_14","en_maths","en","Median of 3,5,7,9,11 is:","5","7","9","6","B","Middle of 5 values is the 3rd = 7."),
        q("em14_5","en_mt_14","en_maths","en","Range = ?","Max-Min","Max+Min","Max×Min","Max/Min","A","Range = Maximum − Minimum value.")
    )

    private fun mathsKnQ() = listOf(
        q("km1_1","kn_mt_1","kn_maths","kn","ಯೂಕ್ಲಿಡ್ ವಿಭಾಗ: a=bq+r ಆದರೆ r?","0<r<b","0≤r<b","0≤r≤b","r>b","B","ಶೇಷ r, 0 ≤ r < b."),
        q("km1_2","kn_mt_1","kn_maths","kn","√2 ಯಾವ ಬಗೆಯ ಸಂಖ್ಯೆ?","ಪರಿಮೇಯ","ಅಪರಿಮೇಯ","ಪೂರ್ಣ","ನೈಸರ್ಗಿಕ","B","√2 ಅಪರಿಮೇಯ ಸಂಖ್ಯೆ."),
        q("km1_3","kn_mt_1","kn_maths","kn","6 ಮತ್ತು 20 ರ MCS:","60","2","6","20","B","MCS = 2."),
        q("km1_4","kn_mt_1","kn_maths","kn","LCM × MCS = ?","ಮೊತ್ತ","ಗುಣಲಬ್ಧ","ವ್ಯತ್ಯಾಸ","ವರ್ಗ","B","LCM × MCS = ಸಂಖ್ಯೆಗಳ ಗುಣಲಬ್ಧ."),
        q("km1_5","kn_mt_1","kn_maths","kn","π ಯಾವ ಸಂಖ್ಯೆ?","ಪರಿಮೇಯ","ಅಪರಿಮೇಯ","ಪೂರ್ಣ","ಭಿನ್ನ","B","π ಅಂತ್ಯಗೊಳ್ಳದ ಅಪುನರಾವರ್ತಿ ಸಂಖ್ಯೆ."),
        q("km4_1","kn_mt_4","kn_maths","kn","ax²+bx+c=0 ರ ವಿವೇಚಕ:","b²+4ac","b²-4ac","2b-4ac","b-4ac","B","D = b²-4ac."),
        q("km4_2","kn_mt_4","kn_maths","kn","D=0 ಆದರೆ ಮೂಲಗಳು:","ನೈಜ ಮೂಲ ಇಲ್ಲ","ಎರಡು ಭಿನ್ನ ನೈಜ ಮೂಲ","ಎರಡು ಸಮಾನ ನೈಜ ಮೂಲ","ಒಂದು ಮೂಲ","C","D=0 → ಎರಡು ಸಮಾನ ಮೂಲ."),
        q("km8_1","kn_mt_8","kn_maths","kn","sin²θ + cos²θ = ?","0","2","1","θ","C","ಪೈಥಾಗೋರಿಯನ್ ಗುರುತು."),
        q("km8_2","kn_mt_8","kn_maths","kn","tan 45° = ?","0","1","√3","1/√3","B","tan 45° = 1."),
        q("km8_3","kn_mt_8","kn_maths","kn","sin 30° = ?","1","1/2","√3/2","0","B","sin 30° = 1/2."),
        q("km14_1","kn_mt_14","kn_maths","kn","2,4,6,8,10 ರ ಸರಾಸರಿ:","4","5","6","7","C","ಮೊತ್ತ=30, n=5, ಸರಾಸರಿ=6."),
        q("km14_2","kn_mt_14","kn_maths","kn","ಅನುಕ್ರಮ ದತ್ತಾಂಶದ ಮಧ್ಯದ ಮೌಲ್ಯ:","ಸರಾಸರಿ","ಮಧ್ಯಮ","ಬಹುಲಕ","ವ್ಯಾಪ್ತಿ","B","ಮಧ್ಯಮ = ಮಧ್ಯದ ಮೌಲ್ಯ."),
        q("km14_3","kn_mt_14","kn_maths","kn","ಹೆಚ್ಚು ಬಾರಿ ಬರುವ ಮೌಲ್ಯ:","ಸರಾಸರಿ","ಮಧ್ಯಮ","ಬಹುಲಕ","ವ್ಯಾಪ್ತಿ","C","ಬಹುಲಕ = ಅತಿ ಹೆಚ್ಚು ಬಾರಿ ಬರುವ ಮೌಲ್ಯ.")
    )

    private fun socialEnQ() = listOf(
        q("ess3_1","en_ss_3","en_social","en","Non-Cooperation Movement started in:","1905","1915","1920","1930","C","Gandhi launched Non-Cooperation in 1920."),
        q("ess3_2","en_ss_3","en_social","en","Dandi March was in:","1920","1930","1940","1942","B","Gandhi's Dandi March — March 1930."),
        q("ess3_3","en_ss_3","en_social","en","Who presided Lahore Congress 1929?","Gandhi","Nehru","Bose","Tilak","B","Nehru presided, Purna Swaraj resolution passed."),
        q("ess3_4","en_ss_3","en_social","en","Quit India Movement was in:","1930","1940","1942","1945","C","8 August 1942 — Quit India."),
        q("ess3_5","en_ss_3","en_social","en","Jallianwala Bagh massacre was in:","1914","1919","1921","1923","B","April 13, 1919 — Amritsar."),
        q("ess3_6","en_ss_3","en_social","en","Rowlatt Act was passed in:","1914","1919","1920","1930","B","Rowlatt Act 1919."),
        q("ess3_7","en_ss_3","en_social","en","'Do or Die' slogan was given by:","Nehru","Tilak","Gandhi","Bose","C","Gandhi — Quit India Movement 1942."),
        q("ess3_8","en_ss_3","en_social","en","Partition of Bengal was in:","1905","1911","1920","1930","A","Lord Curzon partitioned Bengal 1905."),
        q("ess3_9","en_ss_3","en_social","en","Purna Swaraj was declared on:","15 Aug 1947","26 Jan 1950","26 Jan 1930","2 Oct 1930","C","Purna Swaraj declared 26 January 1930."),
        q("ess3_10","en_ss_3","en_social","en","Civil Disobedience Movement began with:","Non-Cooperation","Dandi March","Swadeshi","Khilafat","B","Civil Disobedience began with Dandi March 1930."),
        q("ess9_1","en_ss_9","en_social","en","Which is a biotic resource?","Soil","Water","Plants","Minerals","C","Plants are living (biotic) resources."),
        q("ess9_2","en_ss_9","en_social","en","Sustainable development means:","Use all resources now","Use resources meeting present needs without compromising future","Stop development","Only use renewable resources","B","Sustainable = meeting present without compromising future."),
        q("ess9_3","en_ss_9","en_social","en","Land degradation is caused by:","Afforestation","Overgrazing","Crop rotation","Terrace farming","B","Overgrazing causes land degradation."),
        q("ess9_4","en_ss_9","en_social","en","Laterite soil is found in:","Punjab","Rajasthan","Meghalaya & Karnataka hills","UP","C","Laterite soil in high rainfall areas like Meghalaya, Karnataka hills."),
        q("ess9_5","en_ss_9","en_social","en","Which soil is best for cotton cultivation?","Alluvial soil","Black soil","Red soil","Laterite soil","B","Black (regur) soil is ideal for cotton.")
    )

    private fun socialKnQ() = listOf(
        q("kss3_1","kn_ss_3","kn_social","kn","ಅಸಹಕಾರ ಚಳವಳಿ ಪ್ರಾರಂಭ:","1905","1915","1920","1930","C","1920ರಲ್ಲಿ ಗಾಂಧೀಜಿ ಅಸಹಕಾರ ಚಳವಳಿ."),
        q("kss3_2","kn_ss_3","kn_social","kn","ದಂಡಿ ಯಾತ್ರೆ ನಡೆದ ವರ್ಷ:","1920","1930","1940","1942","B","1930 ಮಾರ್ಚ್‌ನಲ್ಲಿ ದಂಡಿ ಯಾತ್ರೆ."),
        q("kss3_3","kn_ss_3","kn_social","kn","ಭಾರತ ಬಿಟ್ಟು ತೊಲಗಿ ಚಳವಳಿ:","1930","1940","1942","1945","C","8 ಆಗಸ್ಟ್ 1942."),
        q("kss3_4","kn_ss_3","kn_social","kn","ಜಲಿಯನ್‌ವಾಲ ಬಾಗ್ ಹತ್ಯಾಕಾಂಡ:","1914","1919","1921","1923","B","ಏಪ್ರಿಲ್ 13, 1919."),
        q("kss3_5","kn_ss_3","kn_social","kn","'ಮಾಡು ಇಲ್ಲ ಮಡಿ' ಘೋಷಣೆ ನೀಡಿದವರು:","ನೆಹರೂ","ತಿಲಕ","ಗಾಂಧೀಜಿ","ಬೋಸ್","C","1942 ಚಳವಳಿಯಲ್ಲಿ ಗಾಂಧೀಜಿ."),
        q("kss9_1","kn_ss_9","kn_social","kn","ಜೀವಿ ಸಂಪನ್ಮೂಲ ಯಾವುದು?","ಮಣ್ಣು","ನೀರು","ಸಸ್ಯ","ಖನಿಜ","C","ಸಸ್ಯ ಜೀವಿ ಸಂಪನ್ಮೂಲ."),
        q("kss9_2","kn_ss_9","kn_social","kn","ಸುಸ್ಥಿರ ಅಭಿವೃದ್ಧಿ ಎಂದರೆ:","ಎಲ್ಲ ಸಂಪನ್ಮೂಲ ಇಂದೇ ಬಳಸು","ಭವಿಷ್ಯ ತಲೆಮಾರಿನ ಅಗತ್ಯ ಗಮನಿಸಿ ಬಳಸು","ಅಭಿವೃದ್ಧಿ ನಿಲ್ಲಿಸು","ನವೀಕರಿಸಬಹುದಾದದ್ದು ಮಾತ್ರ ಬಳಸು","B","ಸುಸ್ಥಿರ = ಭವಿಷ್ಯ ಗಮನಿಸಿ ಬಳಕೆ."),
        q("kss9_3","kn_ss_9","kn_social","kn","ಭೂ ಅವನತಿಗೆ ಕಾರಣ:","ಅರಣ್ಯೀಕರಣ","ಅತಿ ಮೇಯಿಸುವಿಕೆ","ಬೆಳೆ ಸರದಿ","ಮೆಟ್ಟಿಲು ಕೃಷಿ","B","ಅತಿ ಮೇಯಿಸುವಿಕೆ ಭೂ ಅವನತಿಗೆ ಕಾರಣ.")
    )

    private fun eng1EnQ() = listOf(
        q("ee1_1","en_e1_1","en_english1","en","Who wrote 'A Letter to God'?","Ruskin Bond","G.L. Fuentes","R.K. Narayan","Mulk Raj Anand","B","G.L. Fuentes wrote 'A Letter to God'."),
        q("ee1_2","en_e1_1","en_english1","en","Lencho asked God for:","100 pesos","200 pesos","50 pesos","Food grains","A","Lencho needed 100 pesos."),
        q("ee1_3","en_e1_1","en_english1","en","What destroyed Lencho's crops?","Drought","Hail storm","Flood","Locusts","B","A hail storm destroyed his corn crop."),
        q("ee1_4","en_e1_1","en_english1","en","How much did Lencho receive?","100 pesos","70 pesos","50 pesos","90 pesos","B","He received only 70 pesos."),
        q("ee1_5","en_e1_1","en_english1","en","Lencho called the post office workers:","Angels","Good people","Crooks","Helpers","C","He suspected they stole the money."),
        q("ee2_1","en_e1_2","en_english1","en","What does 'Long Walk to Freedom' mean?","Mandela's literal walk","His struggle against apartheid","A journey to another country","A story about athletes","B","It symbolises his long struggle for freedom."),
        q("ee2_2","en_e1_2","en_english1","en","Where was Nelson Mandela imprisoned?","Cape Town","Johannesburg","Robben Island","Durban","C","He was imprisoned on Robben Island for 27 years."),
        q("ee2_3","en_e1_2","en_english1","en","What is apartheid?","A South African festival","Racial segregation policy","A type of sport","A political party","B","Apartheid = systematic racial segregation in South Africa."),
        q("ee23_1","en_e1_23","en_english1","en","Who wrote 'The Thief's Story'?","R.K. Narayan","Premchand","Ruskin Bond","Tagore","C","Ruskin Bond wrote 'The Thief's Story'."),
        q("ee23_2","en_e1_23","en_english1","en","Who is the main character in 'The Thief's Story'?","Anil","Hari Singh","Ratan","Deepak","B","Hari Singh is the young thief narrator."),
        q("ee23_3","en_e1_23","en_english1","en","What changed the thief's heart?","Money","Anil's trust","Police","His mother","B","Anil's trust and kindness reformed Hari Singh."),
        q("ee23_4","en_e1_23","en_english1","en","What does Hari Singh steal from Anil?","Books","Rupees 600","Food","Clothes","B","He stole rupees 600 from under Anil's mattress."),
        q("ee23_5","en_e1_23","en_english1","en","Why did Hari Singh return the money?","Fear of police","Guilt and gratitude","No buses available","Anil asked him to","B","He returned money due to guilt and Anil's trust.")
    )

    private fun eng2EnQ() = listOf(
        q("ee2q_1","en_e2_1","en_english2","en","'The Little Girl' story theme is:","Friendship","Understanding between children and parents","Animals","School life","B","It explores the relationship between Kezia and her father."),
        q("ee2q_2","en_e2_1","en_english2","en","Who wrote 'The Little Girl'?","Katherine Mansfield","Rabindranath Tagore","R.K. Narayan","Charles Dickens","A","Katherine Mansfield wrote 'The Little Girl'.")
    )

    private fun kannadaQ() = listOf(
        q("kk3_1","en_k1_1","en_kannada1","en","'ಮಳೆ' ಕವಿತೆ ಯಾವ ಭಾವವನ್ನು ವ್ಯಕ್ತಪಡಿಸುತ್ತದೆ?","ದುಃಖ","ಸಂತೋಷ","ಪ್ರಕೃತಿ ಪ್ರೀತಿ","ದೇಶಭಕ್ತಿ","C","ಮಳೆ ಕವಿತೆ ಪ್ರಕೃತಿ ಸೌಂದರ್ಯ ವರ್ಣಿಸುತ್ತದೆ."),
        q("kk3_2","en_k1_3","en_kannada1","en","ಸ್ವರ + ಸ್ವರ ಸೇರಿದಾಗ ಆಗುವ ಸಂಧಿ:","ವ್ಯಂಜನ ಸಂಧಿ","ಸ್ವರ ಸಂಧಿ","ಆಗಮ ಸಂಧಿ","ಲೋಪ ಸಂಧಿ","B","ಎರಡು ಸ್ವರ ಸೇರಿದಾಗ ಸ್ವರ ಸಂಧಿ."),
        q("kk3_3","en_k1_3","en_kannada1","en","'ರವಿ + ಇಂದ್ರ' ಸಂಧಿ ರೂಪ:","ರವೀಂದ್ರ","ರವ್ಯಿಂದ್ರ","ರವಿಂದ್ರ","ರವಿ ಇಂದ್ರ","A","ಇ + ಇ = ಈ; ರವೀಂದ್ರ."),
        q("kkn_1","kn_k1_1","kn_kannada1","kn","'ಮಳೆ' ಕವಿತೆ ಯಾವ ವಿಷಯ ಕುರಿತದ್ದು?","ದುಃಖ","ಸಂತೋಷ","ಪ್ರಕೃತಿ","ದೇಶ","C","ಮಳೆ ಕವಿತೆ ಪ್ರಕೃತಿ ಕುರಿತದ್ದು."),
        q("kkn_2","kn_k1_3","kn_kannada1","kn","ಸ್ವರ ಸಂಧಿ ಯಾವಾಗ ಆಗುತ್ತದೆ?","ಸ್ವರ + ಸ್ವರ","ವ್ಯಂಜನ + ವ್ಯಂಜನ","ಸ್ವರ + ವ್ಯಂಜನ","ಯಾವಾಗಲೂ","A","ಸ್ವರ + ಸ್ವರ ಸೇರಿದಾಗ ಸ್ವರ ಸಂಧಿ.")
    )

    private fun hindiQ() = listOf(
        q("hi1_1","en_hi_1","en_hindi","en","'सूरदास के पद' किस भक्ति धारा के हैं?","राम भक्ति","कृष्ण भक्ति","शिव भक्ति","देवी भक्ति","B","सूरदास कृष्ण भक्त कवि थे।"),
        q("hi1_2","en_hi_1","en_hindi","en","सूरदास किस भाषा में लिखते थे?","हिंदी","ब्रज भाषा","अवधी","संस्कृत","B","सूरदास ब्रज भाषा में लिखते थे।"),
        q("hi2_1","en_hi_2","en_hindi","en","'राम-लक्ष्मण-परशुराम संवाद' किस ग्रंथ से लिया गया है?","रामचरितमानस","महाभारत","वाल्मीकि रामायण","गीतावली","A","तुलसीदास की रामचरितमानस से।"),
        q("hi10_1","en_hi_10","en_hindi","en","'नेताजी का चश्मा' पाठ किस विषय पर है?","शिक्षा","देशभक्ति और अपनेपन की भावना","खेल","व्यापार","B","देशभक्ति और सामान्य लोगों का प्रेम।"),
        q("khi1_1","kn_hi_1","kn_hindi","kn","'ಸೂರದಾಸ ಕೇ ಪದ' ಯಾವ ಭಕ್ತಿ ಧಾರೆಗೆ ಸೇರಿದ್ದು?","ರಾಮ ಭಕ್ತಿ","ಕೃಷ್ಣ ಭಕ್ತಿ","ಶಿವ ಭಕ್ತಿ","ದೇವಿ ಭಕ್ತಿ","B","ಸೂರದಾಸ ಕೃಷ್ಣ ಭಕ್ತ ಕವಿ.")
    )
}
