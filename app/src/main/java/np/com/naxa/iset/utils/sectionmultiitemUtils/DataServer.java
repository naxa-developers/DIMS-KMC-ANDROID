package np.com.naxa.iset.utils.sectionmultiitemUtils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.disasterinfo.HazardListModel;

public class DataServer {

    public static List<SectionMultipleItem> getSectionMultiData() {
        List<SectionMultipleItem> list = new ArrayList<>();
//        MultiItemSectionModel video = new MultiItemSectionModel(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD);

        // add section data
        list.add(new SectionMultipleItem(true, "Kathmandu Metropolitian City", false, false));
        // add multiple type item data ---start---
//        list.add(new SectionMultipleItem(SectionMultipleItem.IMG, new MultiItemSectionModel("http://3.bp.blogspot.com/-SsnyMNpjZWA/UvpdMrkMb-I/AAAAAAAAAGE/8iuSHzis858/s1600/Landuse+Zoning+Map.jpg","", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG, new MultiItemSectionModel("https://www.researchgate.net/profile/Johannes_Anhorn/publication/275581818/figure/fig2/AS:393509669490688@1470831424593/Distribution-of-open-spaces-in-Kathmandu-Metropolitan-City.png", "", "")));
        // ---end---

        list.add(new SectionMultipleItem(true, "General Information", false, false));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Location", "Kathmandu District")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Website", "kathmanduMetro.com.np")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Email", "kathmandumetro@gmail.com")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total Houses", "1,23,45,665")));

        list.add(new SectionMultipleItem(true, "Counts", false, false));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Male", "54564564")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Female", "445445")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total population", "564645645")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Houses", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Hospital", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Schools", "1,23,45,665")));

        list.add(new SectionMultipleItem(true, "Information", false, false));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Male", "54564564")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Female", "445445")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total population", "564645645")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Houses", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Hospital", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Schools", "1,23,45,665")));

        return list;
    }


    public static List<SectionMultipleItem> getThingsToDoBefore() {
        List<SectionMultipleItem> list = new ArrayList<>();
//        MultiItemSectionModel video = new MultiItemSectionModel(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD);

        // add section data

        list.add(new SectionMultipleItem(true, "Always be ready with emergency kit", false, true));
        // add multiple type item data ---start---

        list.add(new SectionMultipleItem(SectionMultipleItem.IMG_TEXT, new MultiItemSectionModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzVmAv8RpDE5LX-5TnDHICNSaEnNL1TuDra4UczpDKiXvWsULP", "Bag Pack", "Kathmandu District")));
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG_TEXT, new MultiItemSectionModel("https://media.istockphoto.com/photos/referee-whistle-picture-id520288272?k=6&m=520288272&s=612x612&w=0&h=pwECJTMFoVYt56JkZevfGJb3CirlP7t05MkUoOLPk1s=", "Whistle", "kathmanduMetro.com.np")));
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG_TEXT, new MultiItemSectionModel("https://previews.123rf.com/images/dariy/dariy1612/dariy161200020/69684345-old-radio-isolated-on-white-background-style-50-ies-of-the-19th-century.jpg", "Radio", "kathmanduMetro.com.np")));
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG_TEXT, new MultiItemSectionModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS78sds-Gau5fd1Xtd_sRmXxeWq0O-dAncNyjfsIJLYgLKcAZbO", "Tourch Light", "1,23,45,665")));

        list.add(new SectionMultipleItem(true, "Plan about the safe place with family", false, true));
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG, new MultiItemSectionModel("https://www.researchgate.net/profile/Johannes_Anhorn/publication/275581818/figure/fig2/AS:393509669490688@1470831424593/Distribution-of-open-spaces-in-Kathmandu-Metropolitan-City.png", "", "")));
        // ---end---

        return list;
    }

    public static List<SectionMultipleItem> getThingsToDoWhenHappens() {
        List<SectionMultipleItem> list = new ArrayList<>();
//        MultiItemSectionModel video = new MultiItemSectionModel(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD);

        // add section data
        list.add(new SectionMultipleItem(true, "Kathmandu Metropolitian City", false, true));
        // add multiple type item data ---start---
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG, new MultiItemSectionModel("https://www.researchgate.net/profile/Johannes_Anhorn/publication/275581818/figure/fig2/AS:393509669490688@1470831424593/Distribution-of-open-spaces-in-Kathmandu-Metropolitan-City.png", "", "")));
        // ---end---

        list.add(new SectionMultipleItem(true, "General Information", false, true));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Location", "Kathmandu District")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Website", "kathmanduMetro.com.np")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Email", "kathmandumetro@gmail.com")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total Houses", "1,23,45,665")));

        list.add(new SectionMultipleItem(true, "Counts", false, true));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Male", "54564564")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Female", "445445")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total population", "564645645")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Houses", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Hospital", "1,23,45,665")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Schools", "1,23,45,665")));


        return list;
    }

    public static List<SectionMultipleItem> getThingsToDoAfter() {
        List<SectionMultipleItem> list = new ArrayList<>();
//        MultiItemSectionModel video = new MultiItemSectionModel(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD);

        // add section data
        list.add(new SectionMultipleItem(true, "Kathmandu Metropolitian City", false, true));
        // add multiple type item data ---start---
        list.add(new SectionMultipleItem(SectionMultipleItem.IMG, new MultiItemSectionModel("https://www.researchgate.net/profile/Johannes_Anhorn/publication/275581818/figure/fig2/AS:393509669490688@1470831424593/Distribution-of-open-spaces-in-Kathmandu-Metropolitan-City.png", "", "")));
        // ---end---

        list.add(new SectionMultipleItem(true, "General Information", false, true));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Location", "Kathmandu District")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Website", "kathmanduMetro.com.np")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Email", "kathmandumetro@gmail.com")));
        list.add(new SectionMultipleItem(SectionMultipleItem.TEXT, new MultiItemSectionModel("", "Total Houses", "1,23,45,665")));

        return list;
    }


    public HazardListModel getEarthquakeDetails() {
        HazardListModel hazardListModel = new HazardListModel();

        hazardListModel.setImage("https://tinyurl.com/yd36gea3");
        hazardListModel.setTitle("Earthquake");
        hazardListModel.setDesc("<p>" +
                "<b>Definition</b> (drr portal)\n\n" +
                "</p>" +
                "<p>" +
                "Nepal on a regular interval witnesses earthquake along the major active" +
                "faults in east-west alignment. Historical data and ongoing seismological" +
                "studies have clearly indicated that the entire region of Nepal is prone to" +
                "earthquake and it lies in the active seismic zone V. It is evident that the" +
                "seismic pattern has geographically divided into three clusters of events;" +
                "viz: western, central and eastern Nepal. It has also pointed out that" +
                "Siwalik, lesser Himalaya and frontal part of the Higher Himalaya are the" +
                "most vulnerable zones. Historical data has shown that the country witnessed" +
                "three major earthquakes in 20th century namely Bihar-Nepal earthquake" +
                "(1934), Bajhang earthquake (1980) and Udayapur earthquake (1988). According" +
                "to Global Report on Disaster Risk, Nepal ranks the 11th position in terms" +
                "of earthquake risk as earthquakes have often occurred in Nepal." +
                "</p>");

        hazardListModel.setBefore_incident("<p><strong>Before an earthquake</strong></p>" +
                "<ul>" +
                "<li>Know what to do during an earthquake </li>" +
                "<li>Identify safe and unsafe places </li>" +
                "<li>Prepare reunification plan </li>" +
                "<li>Prepare communication plan Develop and deploy family emergency plan</li>" +
                "<li>Prepare an emergency kit </li>" +
                "<li>Safeguard your home by regular maintenance </li>" +
                "<li>Conduct earthquake simulation exercise (Drill) </li>" +
                "<li>Acquire Live Saving Specific Skills </li>" +
                "<li>DROP, COVER and HOLD </li>" +
                "<li>Turn off power switches, gas regulators, water lines </li>" +
                "<li>Fire suppression </li>" +
                "<li>Safe Evacuation with &ldquo;GO BAG&rdquo; </li>" +
                "<li>Light search and rescue, SAR </li>" +
                "<li>First aid skills </li>" +
                "<li>Dos and Don&rsquo;ts During and After Earthquake</li>" +
                "</ul>");

        hazardListModel.setDuring_incident("<p><strong>During an Earthquake</strong></p>" +
                "<p>If you are inside a Building Drop, Cover and Hold</p>" +
                "<ul>" +
                "<li>Find a safe place and &ldquo;DROP&rdquo;</li>" +
                "<li>&ldquo;COVER&rdquo; your head and your neck</li>" +
                "<li>&ldquo;HOLD&rdquo; on to something stable</li>" +
                "<li>Take deep breaths and stay calm</li>" +
                "<li>Stay where you are until shaking stops</li>" +
                "</ul>" +
                "<p>If you are trapped inside</p>" +
                "<ul>" +
                "<li>Remain quiet, breathe slowly and believe in your survival</li>" +
                "<li>Panicking and shouting can exhaust you very quickly</li>" +
                "<li>wait for signals &amp; respond with a whistle </li>" +
                "</ul>" +
                "<p><strong>Do not</strong></p>" +
                "<ul>" +
                "<li>Do not Panic and run </li>" +
                "<li>Staircases are usually unsafe!</li>" +
                "<li>Do not jump out from windows, balconies! </li>" +
                "</ul>" +
                "<p>If your are outside</p>" +
                "<ul>" +
                "<li>Get into nearest open space</li>" +
                "<li>If you are in a city, seek shelter under doorways</li>" +
                "<li>Do not try to walk through narrow streets</li>" +
                "<li>Be Careful if you are driving (avoid Bridges, tall buildings, do not stop abruptly)</li>" +
                "</ul>");
        hazardListModel.setAfter_incident("<p><strong>After an Earthquake</strong></p>" +
                        "<ul>" +
                        "<li>Be Prepared for Aftershocks. Lead your family to a previously identified safe place outdoors.</li>" +
                        "<li>Check for Hazards in your house. Put out fires immediately. Turn off gas, electricity and water at main source. Clean up any spilled medicines, drugs, or other harmful materials such as bleach, kerosene. Leave the house if a fire cannot be put out or gas leak is detected.</li>" +
                        "<li>Beware of items falling off shelves when you open doors of cupboards.</li>" +
                        "<li>Check for Food and water supplies. Plan meals to use up food that will spoil quickly. Food in the freezer should be good for at least 2 days, if the door is kept closed. If your water supply is cut off you can drink water from water heaters, melted ice cubes or canned vegetables. Use barbecues or camp stoves outdoors, and only for emergency cooking.</li>" +
                        "<li>Help injured or Trapped People. Administer first-aid (if you know how). DO NOT try to move seriously injured persons unless they are in further danger of injury. Get medical help for the seriously injured.</li>" +
                        "<li>Tie pets and domestic animals. Be wary of all animals.</li>" +
                        "<li>Wear shoes to avoid injury from broken glass and debris.</li>" +
                        "</ul>" +
                        "<ul>" +
                        "<li>DO NOT enter any building.</li>" +
                        "<li>DO NOT turn on the gas again if you have turned it off.</li>" +
                        "<li>DO NOT use matches, lighters, stoves, electrical appliances and equipment until you are sure there are no gas leaks. They m ay create a spark that could ignite leaking gas and cause an explosion and fire</li>" +
                        "<li>DO NOT use your telephone except for a medical or fire emergency. The telephone lines are needed for emergency services</li>" +
                        "<li>DO NOT leave any child alone. Talk and play with the children</li>" +
                        "<li>DO NOT drive. Walk or use a bicycle</li>" +
                        "<li>DO NOT criticize or pass judgment on rescue workers. They are doing it under severe constraints.</li>" +
                        "<li>DO NOT panic and shout for help, if you are trapped inside a collapse building. Breathe slowly and believe in your survival. Response when you hear signals from outside. The best way to response is to blow a whistle.</li>"+
                "</ul>");

        return hazardListModel;
    }

    public HazardListModel getLandslideDetails(){

        HazardListModel hazardListModel = new HazardListModel();
        hazardListModel.setTitle("Landslide");
        hazardListModel.setImage("https://ghnidaniel.files.wordpress.com/2014/08/nepal-landslide-4.jpg");
        hazardListModel.setDesc("<p><strong>Description</strong></p>\n" +
                "<p>Drrportal</p>\n" +
                "<p>Landslide is one of the very common natural hazards in the hilly region of Nepal. Both natural and human factors such as steep slopes, fragile geology, high intensity of rainfall, deforestation, unplanned human settlements are the major causes of landslide. The risk of landslide is further exacerbated by anthropogenic activities like improper land use, encroachment into vulnerable land slopes and unplanned development activities such as construction of roads and irrigation canals without proper protection measures in the vulnerable mountain belt. The hilly districts of Nepal located in the Siwalik, Mahabharat range, Mid-land, and also fore and higher Himalayas are more susceptible to landslide because of steep topography and fragile ecosystem.</p>\n" +
                "<p>NSET</p>\n" +
                "<p>The causes of landslides in Nepal can be assigned to a complex interaction of several factors which are natural as well as human activity related. High relief, concentrated monsoon rainfall, withdraw of underlying as well as lateral supports by toe cutting and bank erosion, presence of weaker rocks, active geotectonic movements and a complex geological history, which has resulted in very intense faulting, folding and fractures of the rocks, are the natural factors causing landslides in Nepal. But human activities are also responsible for the very high extent, and they add to the density of landslides in the country. Overgrazing of protective grassy cover, mass felling of trees leading to an unprecedented deforestation, disturbance of the hill slopes by road/canal construction, non-consideration of the geologic conditions in the corresponding location, planning or designs of infrastructures etc. are some of the important anthropogenic factors leading to landslides. </p>\n" +
                "<p>There are other social causes for the greater extent of damage. Unawareness on the part of the population and the decision makers may be cited as the most important of all the anthropogenic activity related indirect causes of landslides.</p>\n"+
        "<p>All these factors set the stage for the landslide to take place. But the stability balance is usually tipped by one of the two triggers, namely; a) rainfall/cloud burst, and b) earthquake, excavation and transitory stresses.</p>\n" +
                "<p>High intensity rainfalls, which occur frequently during the monsoon season are found to have triggered many highly destructive debris slides and debris flow along the high gradient hill slope channels. Majority of the landslides in Nepal occur in the late monsoon periods. Incessant rainfall during the period, when the antecedent moisture content of the land surface reaches a certain critical stage, is found to be accompanied by landslides. Earthquake is another important landslide triggering factor. Apart from developing fissures both along and parallel to the hill slopes, and thus generating the potentials for debris slides, the earthquakes are found to trigger a variety of landslide types including huge rock slide, rock fall and slumps. Some of the very big landslides have been reported to have been initiated by the Nepal-Bihar earthquake of 1934 AD.</p>");

        hazardListModel.setBefore_incident("<p><strong>Before Landslide</strong></p>\n" +
                "<p>Be Prepared</p>\n" +
                "<ul>\n" +
                "<li>Have an emergency bag ready for your family. This should contain essential items you will need immediately after a landslide. Contents: a torch and batteries, a small radio, enough dry food such as beaten rice or instant noodles for one day, a plastic bottle of drinking water, a bottle of Piyush chlorine drops for purifying drinking water, a basic medical kit and photocopies of your ID cards. Store the bag in a safe place at least 3 feet above ground.</li>\n" +
                "<li>Mitigate your landslide risk by avoiding cutting down trees. Practicing land conservation on farmed hillsides such as terracing is more effective for growing crops. </li>\n" +
                "<li>Prepare a landslide evacuation plan with your family. Agree on a meeting point outside your home in a safe open space away from unstable slopes and cliff edges. Establish evacuation routes from your home to the meeting point</li>\n" +
                "<li>Be alert when in steep river channels. Debris flows can occur without warning, even if it is not raining</li>\n" +
                "<li>To begin preparing, you should build an emergency kit and make a family communications plan.</li>\n" +
                "<li>Prepare for landslides by following proper land-use procedures - avoid building near steep slopes, close to mountain edges, near drainage ways or along natural erosion valleys.</li>\n" +
                "<li>Become familiar with the land around you. Learn whether debris flows have occurred in your area by contacting local officials. Slopes where debris flows have occurred in the past are likely to experience them in the future.</li>\n" +
                "<li>Get a ground assessment of your property.</li>\n" +
                "<li>Consult a professional for advice on appropriate preventative measures for your home or business, such as flexible pipe fittings, which can better resist breakage.</li>\n" +
                "<li>Protect your property by planting ground cover on slopes and building retaining walls.</li>\n" +
                "<li>In mudflow areas, build channels or deflection walls to direct the flow around buildings. Be aware, however, if you build walls to divert debris flow and the flow lands on a neighbor's property, you may be liable for damages.</li>"+
                "</ul>");
        hazardListModel.setDuring_incident("<p><strong>During Landslide</strong></p>\n" +
                "<ul>\n" +
                "<li>If you are trapped in a landslide, use a whistle to alert rescuers. Whistles can be heard easily. They also use less energy than shouting. If you do not have a whistle, make a loud noise by knocking objects together</li>\n" +
                "<li>Monitor the weather for heavy rain which may cause landslides, especially during the rainy season. Listen to local radio stations regularly and watch out for any warnings issued by the local authorities. Follow the advice given by the local authorities to protect yourself and your family against harm from landslides</li>\n" +
                "<li>During heavy rainfall, listen for rumbling sounds that may indicate an approaching landslide. If you hear a rumbling sound, move away from the noise immediately to safer ground ideally uphill and away from the landslide. Be alert for landslides during heavy rainfall. Be especially alert for landslides at night when many people may be asleep</li>\n" +
                "<li>During a severe storm, stay alert and awake. Many deaths from landslides occur while people are sleeping.</li>\n" +
                "<li>Listen to local news stations on a battery-powered radio for warnings of heavy rainfall.</li>\n" +
                "<li>Listen for unusual sounds that might indicate moving debris, such as trees cracking or boulders knocking together.</li>\n" +
                "<li>Move away from the path of a landslide or debris flow as quickly as possible. The danger from a mudflow increases near stream channels and with prolonged heavy rains. Mudflows can move faster than you can walk or run. Look upstream before crossing a bridge and do not cross the bridge if a mudflow is approaching.</li>\n" +
                "<li>Avoid river valleys and low-lying areas.</li>\n" +
                "<li>If you are near a stream or channel, be alert for any sudden increase or decrease in water flow and notice whether the water changes from clear to muddy. Such changes may mean there is debris flow activity upstream so be prepared to move quickly.</li>\n" +
                "<li>Curl into a tight ball and protect your head if escape is not possible.</li>"+
                "</ul>");
        hazardListModel.setAfter_incident("<p><strong>After Landslide</strong></p>\n" +
                "<ul>\n" +
                "<li>After a landslide check for people who may have been trapped in debris. Direct rescuers to their location. Do not enter the landslide area alone. You may also become injured or trapped. </li>\n" +
                "<li>Pay attention to the following landslide warning signs : cracks in land, road or home, dirty underground water sources, tilting trees or retaining walls, springs in previously dry areas, streams that stop flowing and small stones falling for no apparent reason. If you notice these warnings, get to a safe place away from the risk area</li>\n" +
                "<li>Go to a designated public shelter if you have been told to evacuate or you feel it is unsafe to remain in your home. </li>\n" +
                "<li>Stay away from the slide area. There may be danger of additional slides.</li>\n" +
                "<li>Listen to local radio or television stations for the latest emergency information.</li>\n" +
                "<li>Watch for flooding, which may occur after a landslide or debris flow. Floods sometimes follow landslides and debris flows because they may both be started by the same event.</li>\n" +
                "<li>Check for injured and trapped persons near the slide, without entering the direct slide area. Direct rescuers to their locations.</li>\n" +
                "<li>Look for and report broken utility lines and damaged roadways and railways to appropriate authorities. Reporting potential hazards will get the utilities turned off as quickly as possible, preventing further hazard and injury.</li>\n" +
                "<li>Check the building foundation, chimney, and surrounding land for damage. Damage to foundations, chimneys, or surrounding land may help you assess the safety of the area.</li>\n" +
                "<li>Replant damaged ground as soon as possible since erosion caused by loss of ground cover can lead to flash flooding and additional landslides in the near future.</li>\n" +
                "<li>Seek advice from a geotechnical expert for evaluating landslide hazards or designing corrective techniques to reduce landslide risk. A professional will be able to advise you of the best ways to prevent or reduce landslide risk, without creating further hazard.</li>"+
                "</ul>");

        return hazardListModel;

    }

    public String getTerminologies(){

        String terminologies = "<ul>\n" +
                "<li><strong>Acceptable Risk</strong></li>\n" +
                "</ul>\n" +
                "<p>The level of potential losses that a society or community considers acceptable given existing social, economic, political, cultural, technical and environmental conditions.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>स्वीकार्य जोखिम </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>कुनै समाज वा समुदायले विद्यमान सामाजिक, आर्थिक, राजनैतिक, सांस्कृतिक, प्राविधिक तथा वातावरणीय विचार गरेर स्वीकार्य भनि ठहर्याएको संभावित क्षतिको असर</p>\n" +
                "<p><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>Adaptation </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>The adjustment in natural or human systems in response to actual or expected climatic stimuli or their effects, which moderates harm or exploits beneficial opportunities.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>अनुकुलन </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>प्राकृतिक तथा मानवीय क्रियाकलाप औ प्रणालीमा गरिने प्रक्रिया जसले गर्दा वास्तविक वा अपेक्षित जलवायु अदलबदल वा त्यस्ता प्रभावहरुबाट हुने हानी-नोक्सानीको अल्पिकरण गर्ने तथा तद्जन्य उपयोगी अवसरहरुबाट अधिकतम लाभ लिन सकिन्छ .</p>\n" +
                "<p><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>Biological Hazard </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>Process or phenomenon of organic origin or conveyed by biological vectors, including exposure to pathogenic microorganisms, toxins and bioactive substances that may cause loss of life, injury, illness or other health impacts, property damage, loss of livelihood and services, social and economic disruption, or environmental damage.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>जैविक प्रकोप </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>जैविक प्रकृतिका प्रक्रिया वा घटना जसका कारणले ज्यानको नोक्सानी, घाइते, विरामी वा स्वास्थ्य सम्बन्धि अन्य असरहरु, धनमालको क्षति, आजीविका र सेवाहरुको नोक्सानी, सामाजिक-आर्थिक गतिरोध वा वातावरणीय ह्रास निम्त्याउछ . यसमा रोग उत्पादक सुक्ष्म जीव, विषालु पदार्थ वा जैविकरुपले सक्रिय पदार्थहरुसँगको सम्मुखता लगायत जैविक किटाणुले प्रवाहित हुने प्रक्रिया वा घटना पर्दछन .</p>"
                +"<p><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>Building Code </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>A set of ordinances or regulations and associated standards intended to control aspects of the design, construction, materials, alteration and occupancy of structures that are necessary to ensure human safety and welfare, including resistance to collapse and damage.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>भवन निर्माण संहिता </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>मानवीय सुरक्षा तथा सामाजिक भलाइ सुनिश्चित गर्ने प्रयोजनको खातिर भवनको डिजाइन, निर्माण k4lt, निर्माण सामग्री, मर्मत र थपघट तथा उपयोग जस्ता भवनका विभिन्न पक्षहरुलाइ नियमित गरि भवनको संरचनात्मक सुरक्षा र क्षति प्रतिरोधात्मक क्षमता सुनिश्चित गर्न राज्यले प्रतिपादित अध्यादेश वा विनियमहरु तथा सम्बद्ध मापदण्डहरु .</p>\n" +
                "<p><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>Capacity</strong></strong></li>\n" +
                "</ul>\n" +
                "<p>The combination of all the strengths, attributes and resources available within a community, society or organization that can be used to achieve agreed goals.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>क्षमता/ सक्षमता </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>समुदाय, समाज वा संघ-संस्थामा अन्तानिर्हिता विभिन्न प्रकारका शक्ति वा सबलताहरु, विशेषताहरुका साथै त्यस्ता उपलब्ध स्रोत-साधनहरु जसलाई सर्वस्वीकृत लक्ष्य प्राप्तिकालागि उपयोग गर्न सकिन्छ &nbsp;</p>\n" +
                "<p><br /><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>Capacity Development </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>The process by which people, organizations and society systematically stimulate and develop their capacities over time to achieve social and economic goals, including through improvement of knowledge, skills, systems, and institutions.</p>\n" +
                "<ul>\n" +
                "<li><strong><strong>क्षमता विकास </strong></strong></li>\n" +
                "</ul>\n" +
                "<p>त्यस्तो कुराहरु &nbsp;जसले जनता संघसंस्थाहरु तथा समाजलाई समयको अन्तरालमा ज्ञान, सिप, प्रणाली तथा संगठन सुधारका माध्यमबाट सामाजिक एवं आर्थिक लक्ष्य प्राप्त गर्न व्यवस्थित ढंगले उप्प्रेरित गर्ने र तिनीहरुको क्षमताहरुको विकास गर्ने कार्यलाई सघाउछ . यस अन्तर्गत विशेषगरी ज्ञान, सीप, प्रणाली तथा संगठन क्षमताको clea[l4 मार्फत समुदाय, संस्था र समाजले सामाजिक तथा आर्थिक विकासको लक्ष्यहरु परिपूर्तिकालागि आवश्यक सामर्थ्य j[l4 गर्नकालागी गरिने सतत् प्रयास र प्रक्रियाहरु </p>\n"
                +"\n" +
                "<ul>\n" +
                "<li><strong><strong>Climate Change</strong></strong></li>\n" +
                "</ul>\n" +
                "<ol>\n" +
                "<li><strong><em>The Inter-governmental Panel on Climate Change (IPCC) defines climate change as:</em></strong> &lsquo;&lsquo;a change in the state of the climate that can be identified (e.g., by using statistical tests) by changes in the mean and/or the variability of its properties, and that persists for an extended period, typically decades or longer. Climate change may be due to natural internal processes or external forcing, or to persistent anthropogenic changes in the composition of the atmosphere or in land use&rsquo;&rsquo;.</li>\n" +
                "<li><strong><em>The United Nations Framework Convention on Climate Change (UNFCCC)</em></strong> defines climate change as &lsquo;&lsquo;a change of climate which is attributed directly or indirectly to human activity that alters the composition of the global atmosphere and which is in addition to natural climate variability observed over comparable time periods&rsquo;&rsquo;.</li>\n" +
                "</ol>\n" +
                "<p><br /><br /></p>\n" +
                "<ul>\n" +
                "<li><strong><strong>जलवायु परिवर्तन </strong></strong></li>\n" +
                "</ul>\n" +
                "<ul>\n" +
                "<li><strong><em>जलवायु परिवर्तनकालागि अन्तरसरकारी समुहको परिभाषा:</em></strong> जलवायुको अवस्थामा देशको वा अझैँबढी अवधिसम्म अनवरत रुपमा हुने वा भैरहने परिवर्तन जसलाई तथ्यांक विश्लेषणको माध्यमबाट औसत मानमा हुने परिवर्तनको परिमाणद्वारा पहिचान गर्न सकिन्छ. जलवायु परिवर्तनको कारणहरुमा प्राकृतिक रुपमा हुने आन्तरिक प्रक्रिया वा बाह्य कारक तत्वहरु वा मानवजन्य क्रियाकलापले बनोट तथा भू-उपयोगमा ल्याउने ठोस परिवर्तन लगायत पर्दछन</li>\n" +
                "</ul>\n" +
                "<ul>\n" +
                "<li><strong><em>जलवायु परिवर्तन सम्बन्धि राष्ट्रसंघिय संरचना महासभाको परिभाषा:</em></strong> प्रत्यक्ष वा अप्रत्यक्षरुपमा मानव क्रियाकलापले गर्दा विश्वव्यापी रुपमा वायुमण्डलको बनोटमा आउने जन तुलनात्मक समयावधीहरुमा देखिने प्राकृतिक खालका बदलावहरु भन्दा भिन्न परिवर्तन वा अतिरिक्त खालका हुन्छन </li>\n" +
                "</ul>\n";

        return terminologies;
    }


    public static List<SectionMultipleItem> getMapDatacategoryList(@NonNull Context context){
        List<SectionMultipleItem> list = new ArrayList<>();



        list.add(new SectionMultipleItem(true, "Disaster prevention facility", false, false));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("Temporary Shelters", "", context.getResources().getDrawable(R.drawable.ic_industry)));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Evacuation Center", "", context.getResources().getDrawable(R.drawable.ic_marker_openspace))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Evacuation Area", "", context.getResources().getDrawable(R.drawable.ic_marker_openspace))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Water Supply location", "", context.getResources().getDrawable(R.drawable.ic_water_bodies))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Medical institutions", "", context.getResources().getDrawable(R.drawable.ic_hospital))));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_industry", "Temporary Shelters", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_marker_openspace", "Evacuation Center", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_marker_openspace", "Evacuation Area", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_water_bodies", "Water Supply location", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_marker_hospital", "Medical institutions", "health_facilities.geojson")));


        list.add(new SectionMultipleItem(true, "Support Station", false, false));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("Schools run by the Kathmandu Metropolitan Government", "", context.getResources().getDrawable(R.drawable.ic_college))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Convenience Center", "", context.getResources().getDrawable(R.drawable.ic_airport))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Hotel, Restaurants Area", "", context.getResources().getDrawable(R.drawable.ic_restaurant))));
//        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel( "Gas stations", "", context.getResources().getDrawable(R.drawable.ic_gas_station))));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_marker_education", "Schools run by the Kathmandu Metropolitan Government", "educational_Institution_geojson.geojson")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_airport", "Convenience Center", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_restaurant", "Hotel, Restaurants Area", "")));
        list.add(new SectionMultipleItem(SectionMultipleItem.MAP_DATA_LIST, new MultiItemSectionModel("ic_gas_station", "Gas stations", "")));

        return list;
    }
}
