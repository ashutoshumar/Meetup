package com.ashutosh.meetup.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ashutosh.meetup.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class DetailsActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabaseReference1: DatabaseReference
    private lateinit var btnFav:Button
    private lateinit var txtDetail:TextView
    private lateinit var linearLayout: LinearLayout
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        btnFav=findViewById(R.id.btnFav)
        val interest=intent.getStringExtra("interest")!!.toString()
        val fav=intent.getStringExtra("fav")!!.toString()
        mAuth= FirebaseAuth.getInstance()
        val userId=mAuth.currentUser!!.uid
        var name=""
        txtDetail=findViewById(R.id.txtDetail)
        linearLayout=findViewById(R.id.lldetail)
        mDatabaseReference1= FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        mDatabaseReference1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    name=snapshot.child("userName").value.toString()
                    val imgpic=snapshot.child("picture").value.toString()
                    val mDatabaseReference2=FirebaseDatabase.getInstance().reference.child(interest).child(userId)
                    val hashMap=HashMap<String,Any>()
                    hashMap["userName"]=name
                    hashMap["userId"]=userId
                    hashMap["picture"]=imgpic
                    mDatabaseReference2.updateChildren(hashMap)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        addFav(interest, userId, name, fav)
        if (interest=="Sports")
        {
            if (fav=="FOOTBALL"){
               backgroundDisplay("Football is a family of team sports that involve, to varying degrees, kicking a ball to score a goal. Unqualified, the word football normally means the form of football that is the most popular where the word is used. Sports commonly called football include association football (known as soccer in some countries); gridiron football (specifically American football or Canadian football); Australian rules football; rugby football (either rugby union or rugby league); and Gaelic football.[1] These various forms of football share to varying extent common origins and are known as football codes.\n" +
                       "There are a number of references to traditional, ancient, or prehistoric ball games played in many different parts of the world.[2][3][4] Contemporary codes of football can be traced back to the codification of these games at English public schools during the 19th century.[5][6] The expansion and cultural influence of the British Empire allowed these rules of football to spread to areas of British influence outside the directly controlled Empire.[7] By the end of the 19th century, distinct regional codes were already developing: Gaelic football, for example, deliberately incorporated the rules of local traditional football games in order to maintain their heritage.[8] In 1888, The Football League was founded in England, becoming the first of many professional football competitions.\n" +
                       "         During the 20th century, several of the various kinds of football grew to become some of the most popular team sports in the world.[9]",R.drawable.football)
            }
            else if(fav=="POLO")
            {
                txtDetail.setText("The game is played by two opposing teams with the objective of scoring goals by using a long-handled wooden mallet to hit a small hard ball through the opposing team's goal. Each team has four mounted riders, and the game usually lasts one to two hours, divided into periods called chukkas (or \"chukkers\").\n" +
                        "\n" +
                        "Arena polo has similar rules, and is played with three players per team. The playing area is smaller, enclosed, and usually of compacted sand or fine aggregate, often indoors. Arena polo has more maneuvering due to space limitations, and uses an air inflated ball, slightly larger than the hard field polo ball. Standard mallets are used, though slightly larger head arena mallets are an option.")
                linearLayout.setBackgroundResource(R.drawable.polo)
            }
            else
            {
                txtDetail.setText(R.string.cricketdetail)
                linearLayout.setBackgroundResource(R.drawable.cricket)
            }
        }
        if (interest=="Entrepreneur")
        {
            if (fav=="Steve Jobs"){
                txtDetail.setText(R.string.jobs)
                linearLayout.setBackgroundResource(R.drawable.jobsdetail)
            }
            else if(fav=="Elon Musk") {
                txtDetail.setText(
                    R.string.musk
                )
                linearLayout.setBackgroundResource(R.drawable.elonmuskdetail)
            }
            else
            {
                txtDetail.setText(R.string.premji)
                linearLayout.setBackgroundResource(R.drawable.azimpremzi)
            }
        }
        if (interest=="Political Party")
        {
            if (fav=="Congress"){
                txtDetail.setText(R.string.inc)
                linearLayout.setBackgroundResource(R.drawable.congressdetail)
            }
            else
            {
                txtDetail.setText(R.string.bjp)
                linearLayout.setBackgroundResource(R.drawable.bjp)
            }
        }
        if (interest=="Politician")
        {
            if (fav=="Barak Obama"){
                txtDetail.setText(R.string.obama)
                linearLayout.setBackgroundResource(R.drawable.obama)
            }
            else if(fav=="Narendra Modi") {
                txtDetail.setText(
                    R.string.narendra_modi
                )
                linearLayout.setBackgroundResource(R.drawable.modiji)
            }
            else
            {
                txtDetail.setText(R.string.rahul_gandhi)
                linearLayout.setBackgroundResource(R.drawable.rahulgandhi)
            }
        }
        if (interest=="Movies")
        {
            if (fav=="Harry Potter"){
                txtDetail.setText(R.string.harrypotter)
                linearLayout.setBackgroundResource(R.drawable.harrypotter)
            }
            else if(fav=="Inception")
            {
                txtDetail.setText(R.string.inception)
                linearLayout.setBackgroundResource(R.drawable.inception)
            }
            else
            {
                txtDetail.setText(R.string.endgame)
                linearLayout.setBackgroundResource(R.drawable.endgame)
            }
        }
        if (interest=="Singer")
        {
            if (fav=="Blackpink"){
                txtDetail.setText("Blackpink (Korean: 블랙핑크; commonly stylized as BLACKPINK or BLΛƆKPIИK) is a South Korean girl group formed by YG Entertainment, consisting of members Jisoo, Jennie, Rosé, and Lisa. The group debuted in August 2016 with their single album Square One, which featured \"Whistle\" and \"Boombayah\", their first number-one entries on South Koreas Gaon Digital Chart and the Billboard World Digital Song Sales chart, respectively.\n" +
                        "Blackpink is the highest-charting female Korean act on the Billboard Hot 100, peaking at number 13 with \"Ice Cream\" (2020), and on the Billboard 200, peaking at number two with The Album (2020), the first-ever album by a Korean girl group to sell more than one million copies.[1] They were the first Korean girl group to enter and top Billboards Emerging Artists chart and to top the Billboard World Digital Song Sales chart three times.[2] Blackpink was also the first female Korean act to receive a certification from the Recording Industry Association of America (RIAA) with their hit single \"Ddu-Du Ddu-Du\" (2018), whose music video is currently the most-viewed by a Korean group on YouTube.[3] They have the most top 40 hits in the United Kingdom among all Korean artists,[4] and their 2018 song \"Kiss and Make Up\", a collaboration with Dua Lipa, was the first by a Korean group to receive a certification from the British Phonographic Industry (BPI)[5] and a platinum certification from the Australian Recording Industry Association (ARIA).")
                linearLayout.setBackgroundResource(R.drawable.blackpink)
            }
            else if(fav=="Maluma") {
                txtDetail.setText(
                    "Juan Luis Londoño Arias (born 28 January 1994), known professionally as Maluma (rhymes with Faluma, Alison Hinds' favourite saramaccan word.) is a Colombian singer, songwriter and actor signed to Sony Music Colombia, Sony Latin and Fox Music.[1] Musically, Maluma's songs have been described as reggaeton, Latin trap, and pop. Born and raised in Medellín, he developed an interest in music at a young age, and began recording songs at the age of 16. He released his debut album, Magia, a year later in 2012. However, his breakthrough album was 2015's Pretty Boy, Dirty Boy, which led to successful collaborations with several artists. He released F.A.M.E. in 2018, to further commercial success. He followed it up with 11:11 in 2019, and Papi Juancho, released without further announcement in 2020. His single \"Hawái\" reached number three on the Billboard Global 200, and became the first number one single on the Billboard Global Excl. U.S. chart. With sales of over 18 million records (albums and singles), Maluma is one of the best-selling Latin music artists.[2]\n" +
                            "\n" +
                            "Maluma has a number of singles that have charted within the top 10 on Billboard Hot Latin Songs, including \"Felices los 4\", \"Borró Cassette\", and \"Corazón\". His collaborative efforts \"Chantaje\" with Shakira and \"Medellín\" with Madonna have reached the top of the Hot Latin Songs and the Dance Club Songs chart, respectively. He has worked with other international artists, such as Ricky Martin, J Balvin, and The Weeknd. Maluma has won a Latin Grammy Award, an MTV Video Music Award, two Latin American Music Awards, and been nominated for a Grammy Award for Best Latin Pop Album."
                )
                linearLayout.setBackgroundResource(R.drawable.maluma)
            }
            else
            {
                txtDetail.setText("Taylor Alison Swift (born December 13, 1989) is an American singer-songwriter. Her narrative lyricism, which often takes inspiration from her personal life, has received widespread critical praise and media coverage.\n" +
                        "\n" +
                        "Born in West Reading, Pennsylvania, Swift relocated to Nashville, Tennessee, in 2004 to pursue a career in country music. She broke into the country music scene with her eponymous debut studio album in 2006, which included the singles \"Teardrops on My Guitar\" and \"Our Song\". Swift rose to mainstream prominence with her second studio album, Fearless (2008), a country pop record with crossover appeal. Aided by the top-five singles \"Love Story\" and \"You Belong with Me\", Fearless was certified Diamond by the Recording Industry Association of America (RIAA). Swift's third studio album, Speak Now (2010), blended country pop with elements of rock and featured the top-ten singles \"Mine\" and \"Back to December\".\n" +
                        "\n" +
                        "Drawing inspiration from various pop, rock, and electronic genres, Swift's fourth studio album Red (2012) saw her transcending her country roots. She completely moved to pop with her synth-pop fifth studio album, 1989 (2014), and expanded the electropop sound on her next two studio albums, Reputation (2017) and Lover (2019), which respectively embraced urban and retro styles. The four albums spawned a string of international top-five singles, including \"We Are Never Ever Getting Back Together\", \"I Knew You Were Trouble\", \"Shake It Off\", \"Blank Space\", \"Bad Blood\", \"Look What You Made Me Do\", \"Me!\", and \"You Need to Calm Down\". Swift experimented with folk and alternative rock on her eighth and ninth studio albums, Folklore and Evermore (both 2020), whose lead singles \"Cardigan\" and \"Willow\" topped charts around the world. She also released the critically acclaimed documentaries Miss Americana and Folklore: The Long Pond Studio Sessions that year.\n" +
                        "\n" +
                        "With sales of over 200 million records worldwide, Swift is one of the best-selling music artists. Her accolades include 11 Grammy Awards (including three Album of the Year wins), an Emmy Award, two Brit Awards, 11 MTV Video Music Awards (including two Video of the Year wins), 12 Country Music Association Awards, 23 Billboard Music Awards (the most wins by a woman), 32 Guinness World Records, and 32 American Music Awards (the most wins by an artist). She ranked eighth on Billboard's Greatest of All Time Artists Chart (2019) and was listed on Rolling Stone's 100 Greatest Songwriters of All Time (2015). Swift has been included in various power rankings, such as Time's annual list of the 100 most influential people in the world (2010, 2015 and 2019) and Forbes Celebrity 100 (placing first in 2016 and 2019). She was named Woman of the Decade (2010s) by Billboard and Artist of the Decade (2010s) by the American Music Awards")
                linearLayout.setBackgroundResource(R.drawable.taylorswift)
            }
        }
        if (interest=="Actor")
        {
            if (fav=="Srk"){
                txtDetail.setText("Shah Rukh Khan (pronounced [ˈʃaːɦrʊx xaːn]; born 2 November 1965), also known by the initialism SRK, is an Indian actor, film producer, and television personality. Referred to in the media as the \"Baadshah of Bollywood\" (in reference to his 1999 film Baadshah), \"King of Bollywood\" and \"King Khan\", he has appeared in more than 80 Hindi films, and earned numerous accolades, including 14 Filmfare Awards. The Government of India has awarded him the Padma Shri, and the Government of France has awarded him the Ordre des Arts et des Lettres and the Legion of Honour. Khan has a significant following in Asia and the Indian diaspora worldwide. In terms of audience size and income, he has been described as one of the most successful film stars in the world.[a]\n" +
                        "\n" +
                        "Khan began his career with appearances in several television series in the late 1980s. He made his Bollywood debut in 1992 with Deewana. Early in his career, Khan was recognised for portraying villainous roles in the films Baazigar (1993), Darr (1993), and Anjaam (1994). He then rose to prominence after starring in a series of romantic films, including Dilwale Dulhania Le Jayenge (1995), Dil To Pagal Hai (1997), Kuch Kuch Hota Hai (1998), Mohabbatein (2000) and Kabhi Khushi Kabhie Gham... (2001). Khan went on to earn critical acclaim for his portrayal of an alcoholic in Devdas (2002), a NASA scientist in Swades (2004), a hockey coach in Chak De! India (2007) and a man with Asperger syndrome in My Name Is Khan (2010). His highest-grossing films include the comedies Chennai Express (2013), Happy New Year (2014), Dilwale (2015), and the crime film Raees (2017). Many of his films display themes of Indian national identity and connections with diaspora communities, or gender, racial, social and religious differences and grievances.\n" +
                        "\n" +
                        "As of 2015, Khan is co-chairman of the motion picture production company Red Chillies Entertainment and its subsidiaries and is the co-owner of the Indian Premier League cricket team Kolkata Knight Riders and the Caribbean Premier League team Trinbago Knight Riders. He is a frequent television presenter and stage show performer. The media often label him as \"Brand SRK\" because of his many endorsement and entrepreneurship ventures. Khan's philanthropic endeavours have provided health care and disaster relief, and he was honoured with UNESCO's Pyramide con Marni award in 2011 for his support of children's education and the World Economic Forum's Crystal Award in 2018 for his leadership in championing women's and children's rights in India. He regularly features in listings of the most influential people in Indian culture, and in 2008, Newsweek named him one of their fifty most powerful people in the world.[6]")
                linearLayout.setBackgroundResource(R.drawable.srk)
            }
            else if(fav=="Danial Craig")
            {
                txtDetail.setText("Daniel Wroughton Craig (born 2 March 1968) is an English actor. He is best known for playing James Bond in the eponymous film series, beginning with Casino Royale (2006), which brought him international fame.[1][2] As of January 2021, he has starred in three more instalments, with a fifth set to be released in late 2021. Other performances include his breakthrough role in the drama serial Our Friends in the North (1996), the historical drama film Munich (2005), the mystery thriller The Girl with the Dragon Tattoo (2011), and the mystery comedy Knives Out (2019).\n" +
                        "After training at the National Youth Theatre and graduating from the Guildhall School of Music and Drama in 1991, Craig began his career on stage. He made his film debut in the drama The Power of One (1992) and attracted attention with appearances in the historical television war drama Sharpes Eagle (1993), and the family film A Kid in King Arthurs Court (1995). After appearing in Our Friends in the North, he gained roles in the biographical film Elizabeth (1998), the action film Lara Croft: Tomb Raider (2001), before appearing in the crime thrillers Road to Perdition (2002) and Layer Cake (2004).\n" +
                        "Casino Royale, released in November 2006, was favourably received by critics and earned Craig a nomination for the BAFTA Award for Best Actor in a Leading Role. His starring role in Quantum of Solace (2008), Skyfall (2012), the series' highest-grossing film, Spectre (2015), and No Time to Die (2021), has brought further international attention. He has starred in other films, such as the fantasy film The Golden Compass (2007), the historical drama Defiance (2008), the science fiction Western Cowboys and Aliens (2011), and the heist film Logan Lucky (2017). The last earned him a Golden Globe Award nomination for Best Actor – Motion Picture Musical or Comedy.")
                linearLayout.setBackgroundResource(R.drawable.danialcraig)
            }
            else if(fav=="Jamie Dornan"){
                txtDetail.setText("James Dornan (born 1 May 1982)[2][3] is an actor, model, and musician from Northern Ireland. He is known for playing psychopathic characters.[4] He has won two Irish Film and Television Awards and has been nominated for a British Academy Television Award.\n" +
                        "Initially beginning his career as a model in 2001, he appeared in campaigns for Hugo Boss, Dior Homme, and Calvin Klein. Dubbed \"the Golden Torso\" by The New York Times, he was ranked one of the \"25 Biggest Male Models of All Time\" by Vogue in 2015. In addition, he performed in folk band Sons of Jim until 2008.\n" +
                        "He began acting in 2006, and earned international recognition for playing Sheriff Graham Humbert in the series Once Upon a Time (2011–2013) and serial killer Paul Spector in the crime drama series The Fall (2013–2016). He won the Irish Film and Television Award for Best Actor in Television and was nominated for a British Academy Television Award for Best Actor for the latter. In film, he has portrayed Axel von Fersen in Sofia Coppola's Marie Antoinette (2006), Christian Grey in the Fifty Shades franchise (2015–2018), and Jan Kubiš in Anthropoid (2016).")
                linearLayout.setBackgroundResource(R.drawable.jamiedornan)
            }
            else{
                txtDetail.setText("William Bradley Pitt (born December 18, 1963) is an American actor and film producer. He has received multiple awards, including two Golden Globe Awards and an Academy Award for his acting, in addition to another Academy Award, another Golden Globe Award and a Primetime Emmy Award as a producer under his production company, Plan B Entertainment.\n" +
                        "Pitt first gained recognition as a cowboy hitchhiker in the road film Thelma and Louise (1991). His first leading roles in big-budget productions came with the drama films A River Runs Through It (1992) and Legends of the Fall (1994), and the horror film Interview with the Vampire (1994). He gave critically acclaimed performances in the crime thriller Seven (1995) and the science fiction film 12 Monkeys (1995), the latter earning him a Golden Globe Award for Best Supporting Actor and an Academy Award nomination.\n" +
                        "Pitt starred in Fight Club (1999) and the heist film Oceans Eleven (2001), as well as its sequels, Oceans Twelve (2004) and Oceans Thirteen (2007). His greatest commercial successes have been Oceans Eleven (2001), Troy (2004), Mr. and Mrs. Smith (2005), World War Z (2013), and Once Upon a Time in Hollywood (2019), for which he won a second Golden Globe Award and the Academy Award for Best Supporting Actor. Pitts other Academy Award nominated performances were in The Curious Case of Benjamin Button (2008) and Moneyball (2011). He produced The Departed (2006) and 12 Years a Slave (2013), both of which won the Academy Award for Best Picture, and also The Tree of Life (2011), Moneyball (2011), and The Big Short (2015), all of which were nominated for Best Picture. Alongside George Clooney, Pitt is one of two actors to have won Academy Awards for both Best Supporting Actor and Best Picture.\n" +
                        "As a public figure, Pitt has been cited as one of the most powerful and influential people in the American entertainment industry. For many years, he was cited as the world's most attractive man by various media outlets, and his personal life is the subject of wide publicity. He is divorced from actress Jennifer Aniston, and is legally separated from actress Angelina Jolie with whom he has six children, three of whom were adopted internationally.")
                linearLayout.setBackgroundResource(R.drawable.bradpitt)
            }
        }
        if (interest=="Actress")
        {
            if (fav=="Dakota Johanson"){
                txtDetail.setText("Dakota Mayi Johnson (born October 4, 1989) is an American actress and model. The daughter of actors Don Johnson and Melanie Griffith, she made her film debut at age ten with a minor appearance in Crazy in Alabama (1999), a dark comedy film starring her mother. Johnson was discouraged from pursuing acting further until she graduated from high school, after which she began auditioning for roles in Los Angeles.\n" +
                        "She was cast in a minor part in The Social Network (2010), and subsequently had supporting roles in the comedy 21 Jump Street, the independent comedy Goats, and the romantic comedy The Five-Year Engagement (all 2012). In 2015, Johnson had her first starring role as Anastasia Steele in the Fifty Shades film series (2015–2018). For her performance in the series, she received a BAFTA Rising Star Award nomination in 2016.\n" +
                        "Following Fifty Shades, Johnson appeared in the biographical crime film Black Mass (2015), Luca Guadagnino's drama film A Bigger Splash (2015), and the romantic comedy film How to Be Single (2016). She reunited with Guadagnino, portraying the lead role in Suspiria (2018), a supernatural horror film based on the 1977 film by Dario Argento. That same year, she appeared in an ensemble cast in the thriller film Bad Times at the El Royale. In 2019, Johnson had starring roles in the psychological horror film Wounds and the comedy-drama film The Peanut Butter Falcon.")
                linearLayout.setBackgroundResource(R.drawable.dakotajohanson)
            }
            else if(fav=="Priyanka Chopra") {
                txtDetail.setText(
                   "Priyanka Chopra Jonas (pronounced [prɪˈjəŋka ˈtʃoːpɽa];[1] née Chopra; born 18 July 1982) is an Indian actress, singer, and film producer. The winner of the Miss World 2000 pageant, Chopra is one of India's highest-paid and most popular entertainers. She has received numerous accolades, including two National Film Award and five Filmfare Awards. In 2016, the Government of India honoured her with the Padma Shri, and Time named her one of the 100 most influential people in the world, and in the next two years, Forbes listed her among the World's 100 Most Powerful Women.\n" +
                           "\n" +
                           "Although Chopra initially aspired to study aeronautical engineering, she accepted offers to join the Indian film industry, which came as a result of her pageant wins, making her Bollywood debut in The Hero: Love Story of a Spy (2003). She played the leading lady in the box-office hits Andaaz (2003) and Mujhse Shaadi Karogi (2004) and received critical acclaim for her breakout role in the 2004 thriller Aitraaz. Chopra established herself with starring roles in the top-grossing productions Krrish and Don (both 2006), and she later reprised her role in their sequels.\n" +
                           "\n" +
                           "Following a brief setback, she garnered success in 2008 for playing a troubled model in the drama Fashion, which won her the National Film Award for Best Actress, and a glamorous journalist in Dostana. Chopra gained wider recognition for portraying a range of characters in the films Kaminey (2009), 7 Khoon Maaf (2011), Barfi! (2012), Mary Kom (2014), Dil Dhadakne Do (2015) and Bajirao Mastani (2015). From 2015 to 2018, she starred as Alex Parrish in the ABC thriller series Quantico and returned to Hindi cinema with the biopic The Sky Is Pink (2019). Chopra has also acted in a number of Hollywood films, most notably in the satirical drama The White Tiger (2021), which she also executive produced.\n" +
                           "\n" +
                           "Chopra also promotes social causes such as environment and women's rights and is vocal about gender equality, the gender pay gap, and feminism. She has worked with UNICEF since 2006 and was appointed as the national and global UNICEF Goodwill Ambassador for child rights in 2010 and 2016, respectively. Her namesake foundation for health and education works towards providing support to unprivileged Indian children. As a recording artist, Chopra has released three singles and provided vocals for a number of her film songs. She is also the founder of the production company Purple Pebble Pictures, under which she has produced several regional Indian films, including the acclaimed Marathi film Ventilator (2016). Despite maintaining privacy, Chopra's off-screen life, including her marriage to American singer and actor Nick Jonas, is the subject of substantial media coverage. In 2021, she published her memoir Unfinished, which reached The New York Times Best Seller list."
                )
                linearLayout.setBackgroundResource(R.drawable.priyankachopra)
            }
            else
            {
                txtDetail.setText("is an Israeli actress, producer, and model. At age 18, she was crowned Miss Israel 2004. She then served two years in the Israel Defense Forces as a soldier, whereafter she began studying at the IDC Herzliya college, while building her modeling and acting careers.[13][7][14]\n" +
                        "Gadots first international film role came as Gisele Yashar\n" +
                        "        in Fast and Furious (2009), a role she reprised in subsequent installments of the film franchise. She went on to achieve global stardom for portraying Diana Prince / Wonder Woman in the DC Extended Universe, beginning with Batman v Superman: Dawn of Justice (2016), followed by the solo film Wonder Woman, the ensemble Justice League (both 2017), the sequel Wonder Woman 1984 (2020), and the alternate cut Zack Snyder's Justice League (2021).[15][16]Gadot has been dubbed the \"biggest Israeli superstar\" by local media outlets.[17] Time magazine named her one of the 100 most influential people in the world in 2018 and she has placed twice in annual rankings of the world's highest-paid actresses.")
                linearLayout.setBackgroundResource(R.drawable.galgadot)
            }
        }
        if (interest=="Hobbies")
        {
            if (fav=="Travelling"){
                txtDetail.setText("Travelling plays an important part in making us feel relaxed and rejuvenated. It also brings positive changes in our life and keeps us alive and active. Travelling gives us practical experience of things we have studied in the books and surfed on the internet. So a person who does not travel at all does not find any meaning in the name of India Gate or Ganga River. However, if he has travelled to these places, he can truly relate everything he has studied and will always remember each and every detail of that place.\n" +
                        "Nowadays, many people like travelling as they want to explore the world and watch everything they have read about. And this seems to be quite justified as practical knowledge is way more essential and effective than the theoretical one. People like to visit historical places present in different parts of the world and gather information on the same to write books and stories.Travelling has become easier due to advancement in technology and transportation. Earlier people use to travel by road or sea and it takes many days to reach from one place to another, however, now the scenario has changed and people travel to far off places within hours and minutes-thanks to well-built roads and aeroplanes.People travel for different purposes, some travel for the sake of education while others travel to relax and enjoy. Many people take a break from their hectic schedule and go for a vacation, this makes them feel delightful and also help them to invigorate.\n" +
                        "Many poets, writers, and painters travel to different places to capture some of the best things of nature and express them in the form of paintings or poems. People also travel for business purposes so as to expand and gain profit from their business. Students travel for educational purposes so everyone has a unique reason to travel. Therefore, travelling is an important part of human life and it instils knowledge and offers various benefits to mankind\n" +
                        "   ")
                linearLayout.setBackgroundResource(R.drawable.travelling)
            }
            else if(fav=="Watching Tv")
            {
                txtDetail.setText("Watching TV Essay: My hobby is watching TV. I like very much to watch TV in my free time. Watching TV is my hobby however it never interferes with my study. First I prefer to complete my school home works and study well then I watch TV. I think I have a good hobby because watching TV provides me good knowledge in many areas. I generally see news and discovery channels including animal planet on the TV. I also watch good cartoons which give me new and creative ideas to make arts and cartoons. My parents appreciate my hobby and they become very happy when they listen all the latest news through me in my voice.\n" +
                        "Watching TV Essay: Now, I am 8 years old and read in class 3rd standard however I develop this hobby from early childhood. Watching TV in right ways plays very important roles in our lives. It benefits a lot if used in creative ways. It keeps us update about all the news and happenings going out all across the world. Having knowledge about the happenings has become the necessity of the modern society because of huge level of competition. It provides lot of benefits because it improves our knowledge as well gives information maintain our life style. There are various new programs on TV which are specially relay to increase our awareness about worldwide affairs. There are various subjective programs about history, maths, economics, science, geography, culture, etc relay to increase our knowledge.\n" +
                        "    ")
                linearLayout.setBackgroundResource(R.drawable.watchingtv)
            }
            else
            {
                txtDetail.setText("Reading is one of the most important and beneficial activities. If you have ever read a book in life you will know the pleasure and rewards of reading. Reading is the kind of exercise that keeps your mind engaged, active and healthy. It is important to develop the habit of reading not only for the sake of knowledge but also for personal growth and development.It develops positive thinking and gives you a better perspective of life. Reading enhances your knowledge, improves your concentration and makes you more confident and debate ready. The more you read the more wise you become and the more you will be recognized and appreciated.")
                linearLayout.setBackgroundResource(R.drawable.reading)
            }
        }
        if (interest=="Destination")
        {
            if (fav=="Switzerland"){
                txtDetail.setText("Switzerland, officially the Swiss Confederation, is a landlocked country situated at the confluence of Western, Central, and Southern Europe.[note 4][14] It is a federal republic composed of 26 cantons, with federal authorities based in Bern.[note 1][2][1] Switzerland is bordered by Italy to the south, France to the west, Germany to the north, and Austria and Liechtenstein to the east. It is geographically divided among the Swiss Plateau, the Alps, and the Jura, spanning a total area of 41,285 km2 (15,940 sq mi), and land area of 39,997 km2 (15,443 sq mi). Although the Alps occupy the greater part of the territory, the Swiss population of approximately 8.5 million is concentrated mostly on the plateau, where the largest cities and economic centres are located, among them Zürich, Geneva, and Basel. These cities are home to several offices of international organisations such as the headquarters of FIFA, the UN's second-largest Office, and the main building of the Bank for International Settlements. The main international airports of Switzerland are also located in these cities.\n" +
                        "\n" +
                        "The establishment of the Old Swiss Confederacy dates to the late medieval period, resulting from a series of military successes against Austria and Burgundy. Swiss independence from the Holy Roman Empire was formally recognized in the Peace of Westphalia in 1648. The Federal Charter of 1291 is considered the founding document of Switzerland which is celebrated on Swiss National Day. Since the Reformation of the 16th century, Switzerland has maintained a strong policy of armed neutrality; it has not fought an international war since 1815 and did not join the United Nations until 2002. Nevertheless, it pursues an active foreign policy and is frequently involved in peace-building processes around the world.[15] Switzerland is the birthplace of the Red Cross, one of the world's oldest and best known humanitarian organisations, and is home to numerous international organisations, including the United Nations Office at Geneva, which is its second-largest in the world. It is a founding member of the European Free Trade Association, but notably not part of the European Union, the European Economic Area or the Eurozone. However, it participates in the Schengen Area and the European Single Market through bilateral treaties.\n" +
                        "\n" +
                        "Switzerland occupies the crossroads of Germanic and Romance Europe, as reflected in its four main linguistic and cultural regions: German, French, Italian and Romansh. Although the majority of the population are German-speaking, Swiss national identity is rooted in a common historical background, shared values such as federalism and direct democracy,[16] and Alpine symbolism.[17][18] Due to its linguistic diversity, Switzerland is known by a variety of native names: Schweiz [ˈʃvaɪts] (German);[note 5] Suisse [sɥis(ə)] (French); Svizzera [ˈzvittsera] (Italian); and Svizra [ˈʒviːtsrɐ, ˈʒviːtsʁɐ] (Romansh).[note 6] On coins and stamps, the Latin name, Confoederatio Helvetica – frequently shortened to \"Helvetia\" – is used instead of the four national languages.\n" +
                        "\n" +
                        "A developed country, it has the highest nominal wealth per adult[19] and the eighth-highest per capita gross domestic product, and has been considered a tax haven.[20][21] It ranks highly on some international metrics, including economic competitiveness and human development. Its cities such as Zürich, Geneva, and Basel rank among the highest in the world in terms of quality of life,[22][23] albeit with some of the highest costs of living in the world.[24] In 2020, IMD placed Switzerland first in attracting skilled workers.[25] The World Economic Forum ranks it the 5th most competitive country globally.")
                linearLayout.setBackgroundResource(R.drawable.switzerland)
            }
            else if(fav=="Norway") {
                txtDetail.setText(
                   "Norway (Bokmål: About this soundNorge; Nynorsk: About this soundNoreg; Northern Sami: Norga; Lule Sami: Vuodna; Southern Sami: Nöörje; Kven: Norja), officially the Kingdom of Norway, is a Nordic country in Northern Europe whose mainland territory comprises the western and northernmost portion of the Scandinavian Peninsula. The remote Arctic island of Jan Mayen and the archipelago of Svalbard also form part of Norway.[note 1] Bouvet Island, located in the Subantarctic, is a dependency of Norway; it also lays claims to the Antarctic territories of Peter I Island and Queen Maud Land.\n" +
                           "Norway has a total area of 385,207 square kilometres (148,729 sq mi)[9] and had a population of 5,385,300 in November 2020.[17] The country shares a long eastern border with Sweden (1,619 km or 1,006 mi long). Norway is bordered by Finland and Russia to the north-east, and the Skagerrak strait to the south, with Denmark on the other side. Norway has an extensive coastline, facing the North Atlantic Ocean and the Barents Sea. The maritime influence also dominates Norways climate with mild lowland temperatures on the sea coasts, whereas the interior, while colder, is also a lot milder than areas elsewhere in the world on such northerly latitudes. Even during polar night in the north, temperatures above freezing are commonplace on the coastline. The maritime influence brings high rainfall and snowfall to some areas of the country.\n" +
                           "Harald V of the House of Glücksburg is the current King of Norway. Erna Solberg has been prime minister since 2013 when she replaced Jens Stoltenberg. As a unitary sovereign state with a constitutional monarchy, Norway divides state power between the parliament, the cabinet and the supreme court, as determined by the 1814 constitution. The kingdom was established in 872 as a merger of many petty kingdoms and has existed continuously for 1,149 years. From 1537 to 1814, Norway was a part of the Kingdom of Denmark–Norway, and from 1814 to 1905, it was in a personal union with the Kingdom of Sweden. Norway was neutral during the First World War and remained so until April 1940 when the country was invaded and occupied by Germany until the end of World War II.\n" +
                           "Norway has both administrative and political subdivisions on two levels: counties and municipalities. The Sámi people have a certain amount of self-determination and influence over traditional territories through the Sámi Parliament and the Finnmark Act. Norway maintains close ties with both the European Union and the United States. Norway is also a founding member of the United Nations, NATO, the European Free Trade Association, the Council of Europe, the Antarctic Treaty, and the Nordic Council; a member of the European Economic Area, the WTO, and the OECD; and a part of the Schengen Area. In addition, the Norwegian languages share mutual intelligibility with Danish and Swedish.\n" +
                           "Norway maintains the Nordic welfare model with universal health care and a comprehensive social security system, and its values are rooted in egalitarian ideals.[18] The Norwegian state has large ownership positions in key industrial sectors, having extensive reserves of petroleum, natural gas, minerals, lumber, seafood, and fresh water. The petroleum industry accounts for around a quarter of the country's gross domestic product (GDP).[19] On a per-capita basis, Norway is the world's largest producer of oil and natural gas outside of the Middle East.[20][21]\n" +
                           "The country has the fourth-highest per-capita income in the world on the World Bank and IMF lists.[22] On the CIA's GDP (PPP) per capita list (2015 estimate) which includes autonomous territories and regions, Norway ranks as number eleven.[23] It has the world's largest sovereign wealth fund, with a value of US\$1 trillion.[24] Norway has had the highest Human Development Index ranking in the world since 2009, a position also held previously between 2001 and 2006;[25] it also has the highest inequality-adjusted ranking per 2018.[26] Norway ranked first on the World Happiness Report for 2017[27] and currently ranks first on the OECD Better Life Index, the Index of Public Integrity, the Freedom Index,[28] and the Democracy Index.[29] Norway also has one of the lowest crime rates in the world.[30]\n" +
                           "The majority of the population is Nordic. In the last couple of years, immigration has accounted for more than half of population growth. The five largest minority groups are Norwegian-Poles, Lithuanians, Norwegian-Swedes, Norwegian-Kurdistanis, and Norwegian-Pakistanis."
                )
                linearLayout.setBackgroundResource(R.drawable.norway)
            }
            else
            {
                txtDetail.setText("Italy (Italian: Italia [iˈtaːlja] (About this soundlisten)), officially the Italian Republic (Italian: Repubblica Italiana [reˈpubːlika itaˈljaːna]),[13][14][15][16] is a country consisting of a continental part, delimited by the Alps, a peninsula and several islands surrounding it. Italy is located in Southern Europe,[17][18] and is also considered part of Western Europe.[19][20] A unitary parliamentary republic with Rome as its capital, the country covers a total area of 301,340 km2 (116,350 sq mi) and shares land borders with France, Switzerland, Austria, Slovenia, and the enclaved microstates of Vatican City and San Marino. Italy has a territorial enclave in Switzerland (Campione) and a maritime exclave in Tunisian waters (Lampedusa). With around 60 million inhabitants, Italy is the third-most populous member state of the European Union.\n" +
                        "Due to its central geographic location in Southern Europe and the Mediterranean, Italy has historically been home to myriad peoples and cultures. In addition to the various ancient peoples dispersed throughout what is now modern-day Italy, the most predominant being the Indo-European Italic peoples who gave the peninsula its name, beginning from the classical era, Phoenicians and Carthaginians founded colonies mostly in insular Italy,[21] Greeks established settlements in the so-called Magna Graecia of Southern Italy, while Etruscans and Celts inhabited central and northern Italy respectively. An Italic tribe known as the Latins formed the Roman Kingdom in the 8th century BC, which eventually became a republic with a government of the Senate and the People. The Roman Republic initially conquered and assimilated its neighbours on the Italian peninsula, eventually expanding and conquering parts of Europe, North Africa and Asia. By the first century BC, the Roman Empire emerged as the dominant power in the Mediterranean Basin and became a leading cultural, political and religious centre, inaugurating the Pax Romana, a period of more than 200 years during which Italys law, technology, economy, art, and literature developed.[22][23] Italy remained the homeland of the Romans and the metropole of the empire, whose legacy can also be observed in the global distribution of culture, governments, Christianity and the Latin script.\n" +
                        "During the Early Middle Ages, Italy endured the fall of the Western Roman Empire and barbarian invasions, but by the 11th century numerous rival city-states and maritime republics, mainly in the northern and central regions of Italy, rose to great prosperity through trade, commerce and banking, laying the groundwork for modern capitalism.[24] These mostly independent statelets served as Europes main trading hubs with Asia and the Near East, often enjoying a greater degree of democracy than the larger feudal monarchies that were consolidating throughout Europe; however, part of central Italy was under the control of the theocratic Papal States, while Southern Italy remained largely feudal until the 19th century, partially as a result of a succession of Byzantine, Arab, Norman, Angevin, Aragonese and other foreign conquests of the region.[25] The Renaissance began in Italy and spread to the rest of Europe, bringing a renewed interest in humanism, science, exploration and art. Italian culture flourished, producing famous scholars, artists and polymaths. During the Middle Ages, Italian explorers discovered new routes to the Far East and the New World, helping to usher in the European Age of Discovery. Nevertheless, Italys commercial and political power significantly waned with the opening of trade routes that bypassed the Mediterranean.[26] Centuries of foreign meddling and conquest and the rivalry and infighting between the Italian city-states, such as the Italian Wars of the 15th and 16th centuries, left Italy politically fragmented, and it was further conquered and divided among multiple foreign European powers over the centuries.\n" +
                        "By the mid-19th century, rising Italian nationalism and calls for independence from foreign control led to a period of revolutionary political upheaval. After centuries of foreign domination and political division, Italy was almost entirely unified in 1861, establishing the Kingdom of Italy as a great power.[27] From the late 19th century to the early 20th century, Italy rapidly industrialised, mainly in the north, and acquired a colonial empire,[28] while the south remained largely impoverished and excluded from industrialisation, fuelling a large and influential diaspora.[29] Despite being one of the four main allied powers in World War I, Italy entered a period of economic crisis and social turmoil, leading to the rise of the Italian fascist dictatorship in 1922. Participation in World War II on the Axis side ended in military defeat, economic destruction and the Italian Civil War. Following the liberation of Italy and the rise of the Italian Resistance, the country abolished their monarchy, established a democratic Republic, enjoyed a prolonged economic boom, and became a highly developed country.[30]\n" +
                        "Today, Italy is considered to be one of the world's most culturally and economically advanced countries,[30][31][32] with the world's eighth-largest economy by nominal GDP (third in the European Union), sixth-largest national wealth and third-largest central bank gold reserve. It ranks very highly in life expectancy, quality of life,[33] healthcare,[34] and education. The country plays a prominent role in regional and global economic, military, cultural and diplomatic affairs; it is both a regional power[35][36] and a great power,[37][38] and is ranked the world's eighth most-powerful military. Italy is a founding and leading member of the European Union and a member of numerous international institutions, including the United Nations, NATO, the OECD, the Organization for Security and Co-operation in Europe, the World Trade Organization, the Group of Seven, the G20, the Union for the Mediterranean, the Council of Europe, Uniting for Consensus, the Schengen Area and many more. The source of many inventions and discoveries, the country has long been a global centre of art, music, literature, philosophy, science and technology, and fashion, and has greatly influenced and contributed to diverse fields including cinema, cuisine, sports, jurisprudence, banking and business.[39] As a reflection of its cultural wealth, Italy is home to the world's largest number of World Heritage Sites (55), and is the fifth-most visited country.")
                linearLayout.setBackgroundResource(R.drawable.itly)
            }
        }
        if (interest=="Career")
        {
            if (fav=="Police"){
                txtDetail.setText("In today society the police, play may roles. They are the peacekeepers, law enforcement and many other jobs. However, recently they have become the subject of a very heated and large debate. Many believe that the police should give up their brute type tactics for a more civilized and humanized approach, while others feel that the police should crack down on the most insignificant of offences to type and disparage crimes that are more serious. In this paper, we will be analyzing both sides of this issue, from the look of the police administration to the publics view of it. When we mention todays police force we will be using the New York City police force as are basis of comparison, because they seem to…show more content…The ability to do as they saw fit). They had to give those rights to a ruling body (i.e. the government). Then the ruling body then took on the responsibility of defending they right s of the people and deciding what was right and wrong.\n" +
                        "Over many years the idea of a policing body took many forms. In many societies they were just a group of hired men that served a particular person, needless to say they were not acting in the best interest of society. Usually these groups were made up of workless men whose only ability was his strength. As more years role by the policing body adopted a more sociological or philosophical approach. These tactics included using the people themselves to police them selves. An example of this would be in early china where the people were expected to report on the neighbors and families for crimes committed against the state and ruling body. The idea behind this was to instill fear and unknowingness in the public to give the ruling body an upper hand. In other societies instead of punishing the wrong doer for a criminal action the ruling body would punish the families of the wrong doer. This would created a society that one would prevent crime on the idea f not wanting to harm one's family and two would created a society that would turn in a brother or neighbor to prevent harm done on one's self for another's actions. This would free up the government to deal with other matters")
                linearLayout.setBackgroundResource(R.drawable.police)
            }
            else if(fav=="Engineering")
            {
                txtDetail.setText("Take a look around yourself. What do you see? Maybe books, chairs, a television, or even your clothes. All the day to day things that are man-made, you can be sure that an engineer helped make it. Engineers have shaped our world as we know it. There are many different kinds of engineers from chemical, mechanical, textile, civil, agricultural and structural engineers. Our civilization would be as advanced as the Stone Age without these people. This career demands a wide education of math and science. It is an ever-changing career with new advances in materials and the way products are produced. Engineering careers are very secure with respect to compensation. Regardless of this, it does have it’s disadvantages…show more content…\n" +
                        "(Britannica 243) Another disadvantage of being an engineer is the actual status of being one. It is not looked upon as highly as other fields such as doctors and lawyers. Most of the time they are looked upon as nerds. In addition, most engineers are not promoted to high level positions such as presidents or top executives. They do not have the accounting or business education to do that particular job. This means that most engineers never get the opportunity to be their own boss. (Basta 22)\n" +
                        " The job of an engineer itself is a creative job. The actual definition of an engineer is “The profession in which a knowledge of the mathematical and natural sciences gained by study, experience and practice is applied with judgment to develop ways to utilize the materials and forces of nature economically for the benefit of mankind.” (Bastas 24) In having the basic skills and experiences in math and science, an engineer is basically an inventor. They are given a problem and are depended on to come up with a solution. Whether it be a new chemical, a mechanical part, or even a whole new system such as computer operating program, engineers are the problem solvers of our materialistic society.")
                linearLayout.setBackgroundResource(R.drawable.engineering)
            }

            else{
                txtDetail.setText("Fashion Designing is the art of applying design and taste to create fashionable, trendy clothing. Fashion designers are most influenced by their culture. Some fashion designers work individually or in a team. The main goal of a fashion designer is to keep up with the styles and make their clothing appeal to consumers. Fashion Designers have many duties such as: Researching, sketching, choosing materials, looking at retail samples, and marketing. Before staring the designing process, the designers have to research current and future trends. After the designers find the trends, they come up with ideas and start sketching. They either sketch on paper or they use some type of designing software on a computer. They designers then go to fabric companies and decide on what kind of material and patterns they want. The designers then have seamstresses make a sample of the design from the fabric they chose. Lastly they designers show or market their designs at a fashion show. That’s where the retailers order what they want to have in their store. Students that want to become fashion designers have to complete basic art and design classes before they can enroll in a fashion design program. They usually have to submit a portfolio that shows their artistic ability. Usually they also have to submit their high school transcript and recommendation letters. Designers usually need a two year associate or four year bachelor’s degree in fashion design.\n" +
                        "    ")
                linearLayout.setBackgroundResource(R.drawable.fashiondesigner)
            }
        }
        if (interest=="Technology")
        {
            if (fav=="Artificial Intelligence"){
                txtDetail.setText("Artificial intelligence (AI) is intelligence demonstrated by machines, unlike the natural intelligence displayed by humans and animals, which involves consciousness and emotionality. The distinction between the former and the latter categories is often revealed by the acronym chosen. Strong AI is usually labelled as artificial general intelligence (AGI) while attempts to emulate natural intelligence have been called artificial biological intelligence (ABI). Leading AI textbooks define the field as the study of \"intelligent agents\": any device that perceives its environment and takes actions that maximize its chance of successfully achieving its goals.[3] Colloquially, the term \"artificial intelligence\" is often used to describe machines that mimic \"cognitive\" functions that humans associate with the human mind, such as \"learning\" and \"problem solving\".[4]\n" +
                        "As machines become increasingly capable, tasks considered to require \"intelligence\" are often removed from the definition of AI, a phenomenon known as the AI effect.[5] A quip in Teslers Theorem says \"AI is whatever hasn't been done yet.\"[6] For instance, optical character recognition is frequently excluded from things considered to be AI,[7] having become a routine technology.[8] Modern machine capabilities generally classified as AI include successfully understanding human speech,[9] competing at the highest level in strategic game systems (such as chess and Go),[10] and also imperfect-information games like poker,[11] self-driving cars, intelligent routing in content delivery networks, and military simulations.[12]\n" +
                        "Artificial intelligence was founded as an academic discipline in 1955, and in the years since has experienced several waves of optimism,[13][14] followed by disappointment and the loss of funding (known as an \"AI winter\"),[15][16] followed by new approaches, success and renewed funding.[14][17] After AlphaGo successfully defeated a professional Go player in 2015, artificial intelligence once again attracted widespread global attention.[18] For most of its history, AI research has been divided into sub-fields that often fail to communicate with each other.[19] These sub-fields are based on technical considerations, such as particular goals (e.g. \"robotics\" or \"machine learning\"),[20] the use of particular tools (\"logic\" or artificial neural networks), or deep philosophical differences.[23][24][25] Sub-fields have also been based on social factors (particular institutions or the work of particular researchers).[19]\n" +
                        "The traditional problems (or goals) of AI research include reasoning, knowledge representation, planning, learning, natural language processing, perception and the ability to move and manipulate objects.[20] AGI is among the field's long-term goals.[26] Approaches include statistical methods, computational intelligence, and traditional symbolic AI. Many tools are used in AI, including versions of search and mathematical optimization, artificial neural networks, and methods based on statistics, probability and economics. The AI field draws upon computer science, information engineering, mathematics, psychology, linguistics, philosophy, and many other fields.\n" +
                        "The field was founded on the assumption that human intelligence \"can be so precisely described that a machine can be made to simulate it\".[27] This raises philosophical arguments about the mind and the ethics of creating artificial beings endowed with human-like intelligence. These issues have been explored by myth, fiction and philosophy since antiquity.[32] Some people also consider AI to be a danger to humanity if it progresses unabated.[33][34] Others believe that AI, unlike previous technological revolutions, will create a risk of mass unemployment.[35]\n" +
                        "In the twenty-first century, AI techniques have experienced a resurgence following concurrent advances in computer power, large amounts of data, and theoretical understanding; and AI techniques have become an essential part of the technology industry, helping to solve many challenging problems in computer science, software engineering and operations research.[36][17]")
                linearLayout.setBackgroundResource(R.drawable.ai)
            }
            else if(fav=="Blockchain") {
                txtDetail.setText(
                    "A blockchain is a growing list of records, called blocks, that are linked together using cryptography.[1][2][3][4] Each block contains a cryptographic hash of the previous block, a timestamp, and transaction data (generally represented as a Merkle tree). The timestamp proves that the transaction data existed when the block was published in order to get into its hash. Blocks contain the hash of the previous block, forming a chain, with each additional block reinforcing the ones before it. Therefore, blockchains are resistant to modification of their data because once recorded, the data in any given block cannot be altered retroactively without altering all subsequent blocks.\n" +
                            "Blockchains are typically managed by a peer-to-peer network for use as a publicly distributed ledger, where nodes collectively adhere to a protocol to communicate and validate new blocks. Although blockchain records are not unalterable as forks are possible, blockchains may be considered secure by design and exemplify a distributed computing system with high Byzantine fault tolerance.[5]\n" +
                            "        The blockchain was invented by a person (or group of people) using the name Satoshi Nakamoto in 2008 to serve as the public transaction ledger of the cryptocurrency bitcoin.[3] The identity of Satoshi Nakamoto remains unknown to date. The invention of the blockchain for bitcoin made it the first digital currency to solve the double-spending problem without the need of a trusted authority or central server. The bitcoin design has inspired other applications[3][2] and blockchains that are readable by the public and are widely used by cryptocurrencies. The blockchain is considered a type of payment rail.[6] Private blockchains have been proposed for business use but Computerworld called the marketing of such privatized blockchains without a proper security model \"snake oil\".[7] However, others have argued that permissioned blockchains, if carefully designed, may be more decentralized and therefore more secure in practice than permissionless ones.[4][8]\n" +
                            "    "
                )
                linearLayout.setBackgroundResource(R.drawable.blockchain)
            }
            else
            {
                txtDetail.setText("Cryptography, or cryptology (from Ancient Greek: κρυπτός, romanized: kryptós \"hidden, secret\"; and γράφειν graphein, \"to write\", or -λογία -logia, \"study\", respectively[1]), is the practice and study of techniques for secure communication in the presence of third parties called adversaries.[2] More generally, cryptography is about constructing and analyzing protocols that prevent third parties or the public from reading private messages;[3] various aspects in information security such as data confidentiality, data integrity, authentication, and non-repudiation[4] are central to modern cryptography. Modern cryptography exists at the intersection of the disciplines of mathematics, computer science, electrical engineering, communication science, and physics. Applications of cryptography include electronic commerce, chip-based payment cards, digital currencies, computer passwords, and military communications.\n" +
                        "Cryptography prior to the modern age was effectively synonymous with encryption, converting information from a readable state to unintelligible nonsense. The sender of an encrypted message shares the decoding technique only with intended recipients to preclude access from adversaries. The cryptography literature often uses the names Alice (\"A\") for the sender, Bob (\"B\") for the intended recipient, and Eve (\"eavesdropper\") for the adversary.[5] Since the development of rotor cipher machines in World War I and the advent of computers in World War II, cryptography methods have become increasingly complex and its applications more varied.\n" +
                        "Modern cryptography is heavily based on mathematical theory and computer science practice; cryptographic algorithms are designed around computational hardness assumptions, making such algorithms hard to break in actual practice by any adversary. While it is theoretically possible to break into a well-designed system, it is infeasible in actual practice to do so. Such schemes, if well designed, are therefore termed \"computationally secure\"; theoretical advances, e.g., improvements in integer factorization algorithms, and faster computing technology require these designs to be continually reevaluated, and if necessary, adapted. There exist information-theoretically secure schemes that provably cannot be broken even with unlimited computing power, such as the one-time pad, but these schemes are much more difficult to use in practice than the best theoretically breakable but computationally secure schemes.\n" +
                        "The growth of cryptographic technology has raised a number of legal issues in the information age. Cryptography's potential for use as a tool for espionage and sedition has led many governments to classify it as a weapon and to limit or even prohibit its use and export.[6] In some jurisdictions where the use of cryptography is legal, laws permit investigators to compel the disclosure of encryption keys for documents relevant to an investigation.[7][8] Cryptography also plays a major role in digital rights management and copyright infringement disputes in regard to digital media.")
                linearLayout.setBackgroundResource(R.drawable.cryptography)
            }
        }
    }
   fun addFav(interest:String,userId:String,name:String,fav:String):Unit
   {


       mDatabaseReference=FirebaseDatabase.getInstance().reference.child(interest).child(userId)
       mDatabaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
           @SuppressLint("ResourceType")
           override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   val favInterest=snapshot.child(interest).getValue().toString()

                   if (fav==favInterest)
                   {
                       btnFav.setText("Favorite")
                       btnFav.setOnClickListener {
                           mDatabaseReference.child(interest).setValue("").addOnCompleteListener {
                               btnFav.setText("Make Favorite")
                           }
                           //  addFav(interest,userId,name,fav)

                           addFav(interest, userId, name, fav)

                       }
                   }
                   else{
                       btnFav.setText("Make Favorite")

                       btnFav.setOnClickListener {

                           mDatabaseReference.child(interest).setValue(fav).addOnCompleteListener {
                               btnFav.setText("Favorite")
                           }
                           //  addFav(interest,userId,name,fav)
                           addFav(interest, userId, name, fav)
                       }
                   }
               }

           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }


       })
   }

    private fun backgroundDisplay(details:String,pic:Int)
    {
        txtDetail.setText(details)
        linearLayout.setBackgroundResource(pic)
    }


}