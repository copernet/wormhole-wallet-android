package whc.com.whc_wallet;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class PrivacyPolicyActivity extends BaseActivity {
    String terms = "<div data-v-b1240d9e=\"\" data-v-a9fef2c2=\"\" class=\"terms-body\"><p data-v-b1240d9e=\"\" align=\"center\"><a data-v-b1240d9e=\"\" name=\"OLE_LINK9\"></a><a data-v-b1240d9e=\"\" name=\"OLE_LINK8\"><strong data-v-b1240d9e=\"\">Wormhole Wallet Terms</strong></a></p><p data-v-b1240d9e=\"\">\n" +
            "        Last Updated: November 5, 2018.\n" +
            "    </p><div data-v-b1240d9e=\"\" class=\"line\"></div><p data-v-b1240d9e=\"\">\n" +
            "        Dear user,\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        This Wormhole Wallet Terms (“Terms”) is made between the Satoshi Foundation\n" +
            "        (“Wormhole Wallet”, or “Company”) and you (“User”), and is legally binding\n" +
            "        between you and Wormhole Wallet. In this Terms, (a) “we” and “us” refer to\n" +
            "        Wormhole Wallet and “our” shall be construed accordingly; and (b) “you”\n" +
            "        refers to user and “your” shall be construed accordingly. Each of you and\n" +
            "        Wormhole Wallet shall hereinafter be referred to as a “Party”, and\n" +
            "        collectively, you and Wormhole Wallet shall hereinafter be referred to as\n" +
            "        the “Parties”.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        Wormhole Wallet hereby reminds you that\n" +
            "        <strong data-v-b1240d9e=\"\">\n" +
            "            you must carefully read the full content of this Terms and other\n" +
            "            documents mentioned in this Terms before using\n" +
            "        </strong><strong data-v-b1240d9e=\"\">Wormhole Wallet.</strong>\n" +
            "    In particular, you must carefully read “<strong data-v-b1240d9e=\"\">Wormhole Wallet</strong><strong data-v-b1240d9e=\"\">Disclaimer (“the Disclaimer”)</strong>” and the sections which are\n" +
            "        displayed in bold in this Terms. You must make sure that you fully\n" +
            "        understand the whole Terms and evaluate the risks of using Wormhole Wallet\n" +
            "        on your own.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">\n" +
            "            Wormhole Wallet is a project intended for developer testing. The goal\n" +
            "            of Wormhole Wallet is to serve as a reference model in the blockchain\n" +
            "            community for companies that interested in developing related\n" +
            "            technologies. Wormhole Wallet is provided on the pre-condition that the\n" +
            "            users of Wormhole Wallet do not violate any of the relevant applicable\n" +
            "            laws and regulations.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">I. Confirmation and Acceptance of this Terms</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. You understand that this Terms and other relevant documents apply to\n" +
            "        applications and functions that are developed and owned independently by\n" +
            "        Wormhole Wallet. After you accessed the website developed by Wormhole\n" +
            "        Wallet, downloaded the application developed by Wormhole Wallet and start\n" +
            "        to create, or recover identity, you are deemed to have read and accepted\n" +
            "        this Terms, which shall cause this Terms to become effective and legally\n" +
            "        binding on both you and Wormhole Wallet immediately. If you do not agree to\n" +
            "        the terms of this Terms, you shall cease the usage of Wormhole Wallet\n" +
            "        immediately and if you have downloaded the Wormhole Wallet application,\n" +
            "        proceed to delete the Wormhole Wallet application immediately.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        In accessing or using Wormhole Wallet, you agree:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) to be bound by the latest version of this Terms and the Disclaimer\n" +
            "        without variation or modification; and\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) that in the jurisdiction to which you are subject, you are of legal age\n" +
            "        to use the Wormhole Wallet and to create binding legal and financial\n" +
            "        obligations for any liability you may incur as a result of your use of\n" +
            "        Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. The Wormhole Wallet may, at its sole discretion, modify or replace this\n" +
            "        Terms at any time. The modified Terms will automatically take effect once\n" +
            "        it is posted on the Wormhole Wallet website and application, and you will\n" +
            "        not be notified separately. If you do not agree with the modifications, you\n" +
            "        shall cease to use Wormhole Wallet immediately. Your use of Wormhole Wallet\n" +
            "        after any modification to this Terms constitutes your acceptance of this\n" +
            "        Terms as modified.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You warrant that you are at least 18 years old and have complete civil\n" +
            "        capacity when registering or using Wormhole Wallet. If you do not fulfill\n" +
            "        the above requirements, then you shall immediately stop using Wormhole\n" +
            "        Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">II. Definition</strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Blockchain:</strong>\n" +
            "        means a digital ledger in which transactions made in bitcoin or other\n" +
            "        cryptocurrency are recorded chronologically and publicly, which is\n" +
            "        encrypted to ensure the irrevocability and unforgeability of the ledger.\n" +
            "        Blockchain uses chain type data structure to verify and store data, uses\n" +
            "        decentralized node and consensus algorithm to create and update data, uses\n" +
            "        cryptography to ensure the security of the data transmission and access,\n" +
            "        uses automated Smart Contract to program and operate data, and it is a new\n" +
            "        decentralized infrastructure and calculation paradigm.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Digital Asset:</strong>\n" +
            "        in this Terms, Digital Asset means Blockchain based tokens.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Keystore:</strong>\n" +
            "        means Private Key or Mnemonic Words in the format of a file which is\n" +
            "        encrypted and protected by the user’s Wallet Password. Keystore is stored\n" +
            "        only in your mobile device and will not be synchronized to the Company’s\n" +
            "        servers.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Mnemonic Words:</strong>\n" +
            "        consists of 12 (or 15/18/21/24) words which are randomly generated, and it\n" +
            "        is based on BIP39, the industry standard of Blockchain. It is a human\n" +
            "        readable format of words to back up your Private Key for recovery.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Personal Information:</strong>\n" +
            "        means information recorded in electronic or any other form which may\n" +
            "        identify a natural person when used alone or in combination with other\n" +
            "        information, including but not limited to name, date of birth,\n" +
            "        identification card number, personal biological identification information,\n" +
            "        address, telephone number, bank card number, e-mail address, wallet\n" +
            "        address, mobile device information, operation record, transaction record,\n" +
            "        but excluding Wallet Password, Private Key, Mnemonic Words and Keystore.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Private Key:</strong>\n" +
            "        consists of 256 random bits. Private Key is the core for the user to hold\n" +
            "        and use the tokens.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Public Key:</strong>\n" +
            "        is derived from the Private Key based on cryptography and is used to\n" +
            "        generate wallet addresses. A wallet address is a public address for receipt\n" +
            "        of tokens.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Smart Contract:</strong>\n" +
            "        means the type of Smart Contract operating on Ethereum Blockchain and\n" +
            "        spreading, verifying and executing contracts via information methods,\n" +
            "        including but not limited to Smart Contract Kyber and/or Smart Contract 0x\n" +
            "        as hereinafter mentioned. Smart Contracts integrated on Wormhole Wallet do\n" +
            "        not provide cross-Blockchain services.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">Wallet Password:</strong>\n" +
            "        means the password determined by you when you create the wallet. The Wallet\n" +
            "        Password will be used to encrypt and protect your Private Key.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">\n" +
            "            III. Wormhole Wallet Functions (collectively, the “Functions”)\n" +
            "        </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        Wormhole Wallet currently contains the following Functions:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1. <strong data-v-b1240d9e=\"\">Create or Import Wallet.</strong> For tokens that are supported\n" +
            "        by Wormhole Wallet, you may use the Wormhole Wallet website or application\n" +
            "        to create a new wallet or import wallets to manage your tokens.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. <strong data-v-b1240d9e=\"\">Wormhole Wallet Coin (WHC)</strong><strong data-v-b1240d9e=\"\">Creation</strong>.\n" +
            "        Wormhole Wallet allows Users to burn BCH to create WHC. WHC is the basic\n" +
            "        token under Wormhole Contracts. “Burn” means sending a token to a\n" +
            "        designated wallet address which no party has access to the wallet’s private\n" +
            "        key, and thus no party will be able to utilize or transfer such token.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. <strong data-v-b1240d9e=\"\">Smart Property.</strong> Smart Property provides a platform for\n" +
            "        Users to launch their creative projects. Users can launch their own\n" +
            "        projects through Smart Property, and distribute the tokens in exchange for\n" +
            "        WHCs from other Users. Users can also review other users’ Smart Property\n" +
            "        projects and acquire tokens created by these projects. The Users will need\n" +
            "        to burn certain amount of WHCs in order to use this function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. <strong data-v-b1240d9e=\"\">Crowd Sale.</strong> Crowd Sale allows the Users to launch their\n" +
            "        own projects, and other Users can support these projects by sending WHCs to\n" +
            "        these projects. The Users will need to burn certain amount of WHCs in order\n" +
            "        to use this function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. <strong data-v-b1240d9e=\"\">Airdrop.</strong> Wormhole Wallet allows its Users to distribute\n" +
            "        free tokens that are supported by Wormhole Wallet to other Users’ accounts.\n" +
            "        The Users will need to burn certain amount of WHCs in order to use this\n" +
            "        function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. <strong data-v-b1240d9e=\"\">Send All.</strong> This function allows the Users to send all of\n" +
            "        its tokens in Wormhole Wallet to another wallet all at once. The Users will\n" +
            "        need to burn certain amount of WHCs in order to use this function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7. <strong data-v-b1240d9e=\"\">Digital Asset Transfer.</strong> For tokens that are supported\n" +
            "        by Wormhole Wallet (namely BCH, WHC and other supported tokens), you can\n" +
            "        use Wormhole Wallet ’s wallet function to make transfers.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8. <strong data-v-b1240d9e=\"\">Token Management.</strong> You may use Wormhole Wallet to add,\n" +
            "        manage, or delete the tokens that are supported by Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        9. <strong data-v-b1240d9e=\"\">Transaction Records.</strong> The Functions of Wormhole Wallet\n" +
            "        are developed based on Blockchain technology. Wormhole Wallet provides you\n" +
            "        with all or partial transaction records based on the Blockchain technology.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        You hereby understand and agree that: Wormhole Wallet is a project intended\n" +
            "        for developer testing. Wormhole Wallet may be upgraded or under maintenance\n" +
            "        routinely so that our developers of Wormhole Wallet can test their latest\n" +
            "        idea, plan or experiment. The Functions listed above may be modified,\n" +
            "        deleted, or added from time to time. All Functions listed above shall be\n" +
            "        used on an experimental/testing basis.\n" +
            "        <strong data-v-b1240d9e=\"\">\n";

            String term2 =
            "            Please do not use the Functions above to conduct any significant amount\n" +
            "            fund transfer. Wormhole Wallet does not guarantee the security of any\n" +
            "            fund transfer through the use of Wormhole Wallet.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\"><u data-v-b1240d9e=\"\"></u></strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">IV. User Acknowledgement</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        Users hereby acknowledge and accept that:\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">1. </strong><strong data-v-b1240d9e=\"\">\n" +
            "            Wormhole Wallet is not a commercial customer-facing product and it is\n" +
            "            provided only for developer testing. All the Functions of Wormhole\n" +
            "            Wallet are provided for an experimental/testing purpose. You shall not\n" +
            "            use Wormhole Wallet to conduct any significant amount fund transfer and\n" +
            "            given the experimental/testing nature of Wormhole Wallet, Wormhole\n" +
            "            Wallet does not guarantee the security of any fund transfer through the\n" +
            "            use of Wormhole Wallet.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        2. In order to keep the decentralization feature of Blockchain and to\n" +
            "        protect the security of your tokens, Wormhole Wallet offers decentralized\n" +
            "        functions which is largely different from the banking and financial\n" +
            "        institutions. Users acknowledge and accept that the Wormhole Wallet SHALL\n" +
            "        NOT have any responsibility to:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) store user’s Wallet Password, Private Key, Mnemonic Words;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) recover user’s Wallet Password, Private Key, Mnemonic Words;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) freeze wallet at user’s request;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) report the loss of wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        e) recover the wallet; or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        f) rollback transactions.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You shall bear sole responsibility to take care of your computers,\n" +
            "        mobile devices, back up the Wormhole Wallet application, and back up the\n" +
            "        Wallet Password, Mnemonic Words, and Private Key. In the event that: your\n" +
            "        computer or mobile device is lost, your Wormhole Wallet app or your wallet\n" +
            "        is deleted and not backed up, your wallet is stolen or you forget your\n" +
            "        Wallet Password, Private Key, Mnemonic Words, Wormhole Wallet will not be\n" +
            "        able to recover the wallet or recover Wallet Password, Private Key,\n" +
            "        Mnemonic Words. Wormhole Wallet may not be able to cancel transactions for\n" +
            "        Users’ mishandling behavior (for example, such as inputting wrong addresses\n" +
            "        for transactions, wrong amounts to be exchanged) and Wormhole Wallet shall\n" +
            "        not be responsible in any way for any consequence of such mishandling\n" +
            "        behavior.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. Wormhole Wallet only support BCH, WHC and those other tokens that are\n" +
            "        explicitly supported by the wallet system. Do not use Wormhole Wallet to\n" +
            "        handle any non-supported tokens.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. Wormhole Wallet is not an exchange/trade platform. Although this Terms\n" +
            "        may have made multiple references to the term “exchange”, the term\n" +
            "        “exchange” shall be interpreted to mean users using the Functions to\n" +
            "        experimentally transfer and receive tokens, and it is fundamentally\n" +
            "        different from the concept of “exchange” that is used in exchange/trade\n" +
            "        platforms.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. You acknowledge and agree that Wormhole Wallet does not guarantee the\n" +
            "        adequacy, timeliness, truthfulness, accuracy or completeness of any\n" +
            "        information or data provided by other users, and you shall be solely\n" +
            "        responsible to make your own decision and selection. Wormhole Wallet does\n" +
            "        not bear any risk or obligation arising out of the event where other User’s\n" +
            "        activities are declared as illegal, fraud, scam, embezzlement, money\n" +
            "        laundering, bankruptcy or other illegal activities.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7. You acknowledge and agree that Wormhole Wallet may in its sole\n" +
            "        discretion, but is not required to, monitor or control the\n" +
            "        information/content published by Users via the Functions. Wormhole Wallet’s\n" +
            "        failure to exercise this right does not give the User any right to make a\n" +
            "        claim against Wormhole Wallet. Any information/content that has been\n" +
            "        uploaded through Wormhole Wallet may be deleted at any time without notice\n" +
            "        to the User.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8. You acknowledge and agree that Wormhole Wallet is not engaged in\n" +
            "        solicitation of investment or giving investment advice, and Wormhole Wallet\n" +
            "        does not act as an agent or financial agent for any of its Users. Wormhole\n" +
            "        Wallet is a project intended for developer testing. The goal of Wormhole\n" +
            "        Wallet is to serve as a reference model in the blockchain community for\n" +
            "        companies that are interested in developing related technologies.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">V. Your Obligations</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. You shall bear sole responsibility to take care of your computers,\n" +
            "        mobile devices, Wallet Password, Private Key, and Mnemonic Words. Wormhole\n" +
            "        Wallet does not store or hold the above information for users. You shall be\n" +
            "        solely responsible for any risks, liabilities, losses and expenses which\n" +
            "        result from frauds, you losing your computer, mobile device, disclosing\n" +
            "        (whether actively or passively) or forgetting Wallet Password, Private Key,\n" +
            "        Mnemonic Words, or your wallet being attacked.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. Wormhole Wallet may develop different versions of Wormhole Wallet\n" +
            "        application for different terminal devices. You shall use, download and\n" +
            "        install the applicable version developed by Satoshi Foundation. . If you\n" +
            "        use, download and install the Wormhole Wallet application or other\n" +
            "        application with the same name as “Wormhole Wallet” from any unauthorized\n" +
            "        third party, Wormhole Wallet cannot guarantee the normal operation or\n" +
            "        security of such application. Any loss caused by using applications\n" +
            "        developed by an unauthorized third party shall be borne solely by you.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You understand and agree to check for alerts information published by\n" +
            "        the Wormhole Wallet on the Wormhole Wallet website or application regularly\n" +
            "        and comply with those alerts. You shall be responsible for any risks,\n" +
            "        liabilities, losses and expenses which resulted from your failure to comply\n" +
            "        with any alerts.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. To the extent that Wormhole Wallet determines, in its sole discretion,\n" +
            "        that it is necessary to obtain certain information about you in order to\n" +
            "        comply with any applicable law or regulation in connection with the use or\n" +
            "        operation of Wormhole Wallet, you shall provide Wormhole Wallet with such\n" +
            "        information promptly upon such request, and acknowledge and accept that\n" +
            "        Wormhole Wallet may restrict, suspend or terminate your use of Wormhole\n" +
            "        Wallet until such requested information has been provided to the\n" +
            "        satisfaction of Wormhole Wallet. You hereby undertake to notify Wormhole\n" +
            "        Wallet of any change in the documents and information provided by you to\n" +
            "        Wormhole Wallet pursuant to this Terms and in the absence of any written\n" +
            "        notification notifying any change, Wormhole Wallet is entitled to assume\n" +
            "        that the documents and information provided by you remain truthful,\n" +
            "        correct, complete, not misleading and unchanged.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. If Wormhole Wallet reasonably deems your operation or transactions to be\n" +
            "        abnormal, illegal, or considers your identification to be doubtful, or\n" +
            "        Wormhole Wallet considers it necessary to verify your identification\n" +
            "        documents or other necessary documents, you shall cooperate with Wormhole\n" +
            "        Wallet and provide your valid identification documents or other necessary\n" +
            "        documents and complete the identification verification in time.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. Smart Property\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) You understand that Wormhole Wallet does not provide Smart Property\n" +
            "        function in jurisdictions where it is prohibited by law. If you have\n" +
            "        question regarding whether using Smart Property function is legal or not in\n" +
            "        certain jurisdiction, please consult independent legal advice before using\n" +
            "        this function. The User is solely responsible for understanding and\n" +
            "        complying with any and all laws, rules and regulations of his/her specific\n" +
            "        jurisdiction that may be applicable to the User in connection with the use\n" +
            "        of all Functions of Wormhole Wallet. Wormhole Wallet, as well as any other\n" +
            "        person, authorized by Wormhole Wallet to provide the function, shall not be\n" +
            "        held liable for any legal risks and disputes arising in the jurisdiction of\n" +
            "        User's residency. You further represent and warrant that you: (a) have not\n" +
            "        previously been suspended or removed from using Wormhole Wallet; (b)\n" +
            "        entering into this Terms will not violate any other agreement to which you\n" +
            "        are a party; (c) will not use Wormhole Wallet if any applicable laws in\n" +
            "        your country/residency prohibit you from doing so.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) You need to burn certain amount of WHCs in order to launch a Smart\n" +
            "        Property project. When launching a Smart Property project, you shall\n" +
            "        provide timely, adequate, accurate and truthful information and data about\n" +
            "        the project, including the major risks of your project, and shall not use\n" +
            "        Smart Property function for any illegal or criminal purposes or in\n" +
            "        violation of any applicable law.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) When acquiring tokens created by other Users through Smart Property, you\n" +
            "        shall independently determine the accuracy, timeliness, completeness and\n" +
            "        adequacy of the information and data provided, and decide whether to\n" +
            "        acquire such token based on your own judgement. You understand Wormhole\n" +
            "        Wallet does not act as adviser or agents of any project launched by the\n" +
            "        User and Wormhole Wallet does not provide investment, tax, accounting,\n" +
            "        financial, or legal advice. No communication or information provided to\n" +
            "        User by Wormhole Wallet shall be considered or construed as advice,\n" +
            "        recommendation or representation of any kind. Wormhole Wallet makes no\n" +
            "        representation or warrants as to the adequacy, timeliness, accuracy,\n" +
            "        truthfulness or completeness of any information of the project launched by\n" +
            "        the User.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7. Crowd Sale\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) You understand that Wormhole Wallet does not provide Crowd Sale function\n" +
            "        in jurisdictions where it is prohibited by law. If you have question\n" +
            "        regarding whether using Crowd Sale function is legal in certain\n" +
            "        jurisdiction, please consult independent legal advices before using this\n" +
            "        Function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) You need to burn certain amount of WHCs in order to launch a Crowd Sale\n" +
            "        project. When launching a Crowd Sale project, you shall provide adequate,\n" +
            "        complete, timely, accurate and truthful information and data about the\n" +
            "        project, including the major risks of your project, and shall not use Crowd\n" +
            "        Sale function for any illegal or criminal purposes or in violation of any\n" +
            "        applicable law.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) When supporting a Crowd Sale project launched by another user, you shall\n" +
            "        independently determine the accuracy, completeness of the information and\n" +
            "        data provided, and decide whether to support such project based on your own\n" +
            "        judgement. You understand Wormhole Wallet does not act as adviser or agents\n" +
            "        of any project launched by the User and Wormhole Wallet does not provide\n" +
            "        investment, tax, accounting, financial, or legal advice. No communication\n" +
            "        or information provided to User by Wormhole Wallet shall be considered or\n" +
            "        construed as advice. Wormhole Wallet makes no representation or warrants as\n" +
            "        to the adequacy, timeliness, accuracy, truthfulness or completeness of any\n" +
            "        information of the project launched by the User.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8. Transfer of Tokens\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">a) </strong><strong data-v-b1240d9e=\"\">\n" +
            "            You understand Wormhole Wallet is not a commercial customer-facing\n" +
            "            product and it is provided only for developer testing. All the\n" +
            "            Functions of Wormhole Wallet are provided for an experimental/testing\n" +
            "            purpose. You shall not use Wormhole Wallet to conduct any significant\n" +
            "            amount fund transfer, and given the experimental/testing nature of\n" +
            "            Wormhole Wallet, Wormhole Wallet does not guarantee the security of any\n" +
            "            fund transfer through the use of Wormhole Wallet.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        b) You understand that Blockchain operations are “irrevocable”. When you\n" +
            "        use the Functions to transfer tokens, you shall be solely responsible for\n" +
            "        the consequences of your mishandling of the transfer (including but not\n" +
            "        limited to typing in wrong address, selecting node servers with problems).\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) You understand that the following reasons may result in a “failed\n" +
            "        transfer”:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        - insufficient balance in wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        - the transfer amount exceeding the transfer limits imposed by authorities,\n" +
            "        Wormhole Wallet or applicable laws or regulations;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        - technical failure of the network or equipment;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        - abandoned transactions result from Blockchain network congestion or\n" +
            "        failure;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        - the wallet address of yours or your counterparty’s is identified as “red\n" +
            "        flagged” addresses, such as high-risk address.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        9. You understand that you shall abide by applicable laws, regulations and\n" +
            "        policies when you use the Functions.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        10. Wormhole Wallet may send notifications to you by web announcements,\n" +
            "        e-mails, text messages, phone calls, Notification Centre information, popup\n" +
            "        tips or client-end notices (e.g., information about your transfer or\n" +
            "        instructions on certain operations), which you shall check timely.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        11. Fees and Taxes.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) You need to burn certain amount of WHCs in order to use certain\n" +
            "        Functions provided by Wormhole Wallet, such as launching a Smart Property\n" +
            "        project or a Crowd Sale project. You acknowledge and agree that the WHCs\n" +
            "        that are burned for these Functions shall not be treated as service fees\n" +
            "        paid to Wormhole Wallet, as Wormhole Wallet does not have access to such\n" +
            "        WHCs;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) Wormhole Wallet reserves the right to revise the fee arrangement in the\n" +
            "        future without providing prior notice to you;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) You understand that your transfer of tokens may fail under certain\n" +
            "        circumstances;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) You shall bear all the applicable taxes and other expenses occurred due\n" +
            "        to your transactions during your use of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">VI. Representations and Warranties: </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. You agree that to use the Functions of Wormhole Wallet, you need to open\n" +
            "        Wormhole Wallet web version on your computer and/or download Wormhole\n" +
            "        Wallet application on your mobile device and you shall be responsible to\n" +
            "        take care of your Private Key.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. You shall not use Wormhole Wallet to conduct crimes or illegal\n" +
            "        activities or other misconducts, including but not limited to:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) infringe other person’s legal rights of reputation, privacy, commercial\n" +
            "        secrets, trademarks, copyrights and patents;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) jeopardize state security of your residence countries or areas, leak\n" +
            "        state secrets, overthrow government power, damage state unity;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) use any type of automated programs, software, engines, internet worm,\n" +
            "        website analysis tools, data mining tools, or similar tools, to get access\n" +
            "        to the Functions, collect or process any content provided by Wormhole\n" +
            "        Wallet, intervene or attempt to intervene any users;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) invade into other user’s wallet/account and/or steal tokens;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        e) conduct transactions that are not consistent with the contents provided\n" +
            "        by your counterpart, or conduct unreal or fraudulent transactions;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        f) conduct behavior that damages or may damage Wormhole Wallet system or\n" +
            "        data;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        g) violate statutory or agreed confidential obligations；\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        h) engage in any criminal or illegal activities, including but not limited\n" +
            "        to money laundering or illegal fund raising activities;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        i) provide gambling information or fake information or induce others to\n" +
            "        engage in gambling or illegal financial activities;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        j) conduct any behavior that may transmit computer virus or damage Wormhole\n" +
            "        Wallet system; and\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        k) other behaviors that Wormhole Wallet reasonably deems as misconduct.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You acknowledge and accept that you shall indemnify and hold harmless\n" +
            "        Wormhole Wallet from any and all liabilities, claims, judgments, losses,\n" +
            "        fines, penalties, expenses and any costs relating thereto (including but\n" +
            "        not limited to court costs and reasonable attorney fees) in respect of your\n" +
            "        violation of applicable laws and regulations (including but not limited to\n" +
            "        statutory rules on customs and tax) or this Terms.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. You undertake to pay relevant fee (if any) in a timely manner, otherwise\n" +
            "        Wormhole Wallet shall have the right to suspend your access Wormhole Wallet\n" +
            "        and or its any Function.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">VII. Risks</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. You acknowledge and agree that the Blockchain technology is a field of\n" +
            "        innovation, where laws and regulations are not fully established. You\n" +
            "        acknowledge and agree that you may face material risks, including but not\n" +
            "        limited to the instability of technology, failure of the fiat-token\n" +
            "        exchange. You also acknowledge and agree that tokens have much higher\n" +
            "        volatility comparing to other financial assets. You shall hold or dispose\n" +
            "        tokens in a reasonable way, which corresponds to your financial status and\n" +
            "        risk preferences.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">2. </strong><strong data-v-b1240d9e=\"\">\n" +
            "            You acknowledge and agree that Wormhole Wallet is not a commercial\n" +
            "            customer-facing product and it is provided only for developer testing.\n" +
            "            Given the experimental/testing nature of Wormhole Wallet, we cannot\n" +
            "            guarantee your use of Wormhole Wallet is safe or error-free. You shall\n" +
            "            not use Wormhole Wallet to conduct any significant amount fund transfer\n" +
            "            and Wormhole Wallet does not guarantee the security of any fund\n" +
            "            transfer through the use of Wormhole Wallet.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\"></strong></p><p data-v-b1240d9e=\"\">\n" +
            "        3. If you or your counterparty fails to comply with this Agreement or fails\n" +
            "        to follow the instructions, notifications or rules published by Wormhole\n" +
            "        Wallet on its website, mobile application or the transaction or payment\n" +
            "        page, Wormhole Wallet does not guarantee successful operation of any\n" +
            "        Services and Wormhole Wallet shall not be held liable for any of the\n" +
            "        consequences thereof. If you or your counterparty has already received the\n" +
            "        payment in the wallet or third-party wallet, you understand that such\n" +
            "        transactions on Blockchain are irreversible and irrevocable. You and your\n" +
            "        counterparty shall assume all the liabilities and consequences of your\n" +
            "        transactions.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. When you transfer your tokens directly to another wallet address, it is\n" +
            "        your sole responsibility to make sure that your counterparty is a person\n" +
            "        with full capacity for civil acts and decide whether you shall transact\n" +
            "        with him/her.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. You shall check the official Blockchain system or other Blockchain tools\n" +
            "        when you receive an alert such as “transaction failed” or “mining overtime”\n" +
            "        in order to avoid repetitive transfer. If you fail to follow this\n" +
            "        instruction, you shall bear the losses and expenses occurred due to such\n" +
            "        repetitive transfer.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. You understand that after you created or imported wallet on Wormhole\n" +
            "        Wallet system, Private Key and Mnemonic Words are only stored on your\n" +
            "        computer and/or mobile device and will not be stored on the servers of\n" +
            "        Wormhole Wallet. You may change another computer or mobile device to use\n" +
            "        the Functions after you follow the instructions to back up your wallet. If\n" +
            "        you lose your computer or mobile device before you could write down or\n" +
            "        backup your Wallet Password, Private Key, Mnemonic Words, you may lose your\n" +
            "        tokens and Wormhole Wallet will not be able to recover them. If your Wallet\n" +
            "        Password, Private Key, Mnemonic Words is disclosed or the device which\n" +
            "        stores or holds your Wallet Password, Private Key, Mnemonic Words is hacked\n" +
            "        or attacked, you may lose your tokens and Wormhole Wallet will not be able\n" +
            "        to recover them. Any and all losses arising in connection with the\n" +
            "        foregoing shall be borne solely by you.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7. We suggest you back up your Wallet Password, Private Key, Mnemonic Words\n" +
            "        when you create or import wallet by writing them down on papers or back up\n" +
            "        them in password management apps. Please do not use electronic methods such\n" +
            "        as screenshots, e-mails, note-taking apps in cell phones, text messages, or\n" +
            "        any messaging app such as WeChat or QQ to backup any of the foregoing\n" +
            "        information.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8. In order to avoid potential security risks, we suggest you use the\n" +
            "        Functions in a secured network environment. Please do not use a jailbreak\n" +
            "        or rooted mobile device.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        9. Please be alert to frauds when you use Wormhole Wallet. If you find any\n" +
            "        suspicious activity, we encourage you to inform us immediately.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">\n" +
            "            VIII Change, Suspension, Termination of Wormhole Wallet Use\n" +
            "        </strong><strong data-v-b1240d9e=\"\"></strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1.\n" +
            "        <strong data-v-b1240d9e=\"\">\n" +
            "            You acknowledge and agree that, Wormhole Wallet may be upgraded or\n" +
            "            under maintenance routinely so that our developers of Wormhole Wallet\n" +
            "            can test their latest idea, plan or experiment.\n" +
            "        </strong>\n" +
            "        Wormhole Wallet may need to carry out inspection and maintenance on our\n" +
            "        internet services providing platforms, such as websites or mobile network,\n" +
            "        or on related equipment thereof, and the ability to connect to the internet\n" +
            "        is subject to limitations on stability of global network, technology\n" +
            "        status, residence of user, network in use, power supply, government\n" +
            "        restrictions, computer virus, hacking, and other uncertain factors.\n" +
            "        Wormhole Wallet is not liable to any function suspension due to the\n" +
            "        abovementioned situations, and Wormhole Wallet undertakes no obligation to\n" +
            "        inform you in advance unless such suspension is reasonably anticipated by\n" +
            "        Wormhole Wallet and Wormhole Wallet has reasonable time to inform you in\n" +
            "        advance.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. You acknowledge and accept that Wormhole Wallet may, in its sole\n" +
            "        discretion, provide only a part of the Functions for the time being,\n" +
            "        suspend certain function or provide new functions in the future. When we\n" +
            "        change the Functions we provide, your continuous use of Wormhole Wallet is\n" +
            "        deemed as your acceptance of this Terms and revisions of this Terms.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You understand that Wormhole Wallet may suspend the Functions under the\n" +
            "        following circumstances (or may completely terminate the Functions in\n" +
            "        connection with any of the following circumstances):\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) maintenance, upgrading, failure of equipment and Blockchain system and\n" +
            "        the interruption of communications etc., which lead to the operation\n" +
            "        suspension of Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) force majeure events including but not limited to typhoon, earthquake,\n" +
            "        tsunami, flood, power outage, war, or terrorist attacks, or computer\n" +
            "        viruses, Trojan horse, hacker attacks, system instability or government\n" +
            "        behaviors and other reasons, which result in the Wormhole Wallet’s\n" +
            "        inability to provide the Functions or if in Wormhole Wallet’s reasonable\n" +
            "        opinion, continuous provision of the Functions would result in significant\n" +
            "        risks;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) material adverse change of applicable laws or policies; or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) any other event(s) which Wormhole Wallet cannot control or reasonably\n" +
            "        predict.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. Wormhole Wallet reserves the right to unilaterally suspend or terminate\n" +
            "        any function of Wormhole Wallet under the following circumstances:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) death of users;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) if you steal others’ wallets information;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) if you provide false information to Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) if you refuse to allow mandatory update of Wormhole Wallet applications;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        e) if you use Wormhole Wallet to commit illegal or criminal activities;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        f) if you hinder the normal use of Wormhole Wallet by other users;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        g) if you pretend to be staff or management personnel of Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        h) if you threaten the normal operation of Wormhole Wallet computer system\n" +
            "        by attack, invasion, alternation or any other means;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        i) if you use Wormhole Wallet to send spam; or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        j) if you spread rumors which endanger the goodwill and reputation of\n" +
            "        Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        k) if you conduct any illegal activities, breach this Terms or in other\n" +
            "        circumstances under which Wormhole Wallet reasonably considers necessary to\n" +
            "        suspend your use of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. If your actions or approach in use of Wormhole Wallet violates any laws,\n" +
            "        regulations, or government policies, or infringes any third party’s legal\n" +
            "        rights, Wormhole Wallet has the right to terminate your use of such\n" +
            "        account, or in accordance with related requirements of laws or governmental\n" +
            "        order, report corresponding information to the relevant governmental\n" +
            "        authorities.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. You are entitled to export your wallets within a reasonable amount of\n" +
            "        time if Wormhole Wallet changes, suspends or terminates its Functions.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">IX Protections of Intellectual Properties</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. The intellectual property rights of any contents displayed in all\n" +
            "        platforms of Wormhole Wallet and its affiliates, including but not limited\n" +
            "        to articles, pictures, archives, news, materials, website structure,\n" +
            "        website arrangement, website design, are solely owned by Wormhole Wallet\n" +
            "        and its affiliates.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. Without receiving the prior written consent from Wormhole Wallet and its\n" +
            "        affiliates, no one shall use, modify, decompile, reproduce, publicly\n" +
            "        disseminate, alter, distribute, issue or publicly publish applications and\n" +
            "        contents of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. You should respect the intellectual property rights of Wormhole Wallet,\n" +
            "        and you shall be liable for any and all losses and damages incurred to\n" +
            "        Wormhole Wallet and its affiliates in event of your violation of this\n" +
            "        obligation.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. Wormhole Wallet does not own the information and data you submit to us\n" +
            "        (your “Content”), but we do need certain licenses from you in order to\n" +
            "        provide certain functions. When you launch a project, you agree to the\n" +
            "        following terms:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) You grant to us the worldwide, non-exclusive, perpetual, irrevocable,\n" +
            "        royalty-free, sublicensable, transferable right to use, exercise,\n" +
            "        commercialize, and exploit the copyright, publicity, trademark and database\n" +
            "        rights with respect to your Content;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) You grant us the right to edit, modify, reformat, excerpt, delete, or\n" +
            "        translate any of your Content;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) Your Content will not contain third-party copyrighted material, or\n" +
            "        material that is subject to other third-party proprietary rights, unless\n" +
            "        you have permission from the rightful owner of the material, or you are\n" +
            "        otherwise legally entitled to post the material;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        d) You will pay all royalties and other amounts owed to any person or\n" +
            "        entity based on your Content;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        e) If we use any of your Contents, you promise that this will not infringe\n" +
            "        or violate the rights of any third party, including without limitation any\n" +
            "        privacy rights, publicity rights, copyrights, contract rights, or any other\n" +
            "        intellectual property or proprietary rights;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        f) All information submitted to us, whether publicly posted or privately\n" +
            "        transmitted, is the sole responsibility of the person from whom that\n" +
            "        Content originated;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        g) We will not be liable for any errors or omissions in any Content.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">X Governing Laws and Jurisdiction</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        This Terms, including its validity, interpretation, effect, fulfillment,\n" +
            "        and remedies, shall be subject to the laws of Singapore without\n" +
            "        jeopardizing the rights under the choice of law rules. In case of any\n" +
            "        disputes or claims (issue) arising out of this Terms or its interpretation,\n" +
            "        explanation, fulfillment, default, termination, enforceability, or\n" +
            "        validity, the party who lodged such issue shall inform the other party\n" +
            "        within 60 days as of the date of discovering the issue. Both parties shall\n" +
            "        cooperate to settle such dispute on a good faith basis and without delay.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">XI Arbitration</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        If there is any dispute that cannot be amicably settled, both parties agree\n" +
            "        to submit such dispute to arbitration. The arbitration shall be submitted\n" +
            "        to Singapore International Arbitration Center (“SIAC”) in accordance with\n" +
            "        SIAC Rules (“SIAC Rules”) in effect before one arbitrator to be appointed\n" +
            "        according to the SIAC Rules. The decision and awards of the arbitration\n" +
            "        shall be final and binding upon the parties hereto.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">XII</strong><strong data-v-b1240d9e=\"\"> Term of this Terms </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        This Terms entered between you and Wormhole Wallet does not have a fixed\n" +
            "        term. The User may terminate this Terms at any time by deleting any content\n" +
            "        you have provided to Wormhole Wallet, ceasing to use Wormhole Wallet, and\n" +
            "        sending a request to Wormhole Wallet at support@wormhole.cash, in order to\n" +
            "        ensure that the account is disabled. Wormhole Wallet has the right to\n" +
            "        terminate this Terms at any time, particularly if the User is suspected of\n" +
            "        conducting any of the following: 1, violation of any provision of this\n" +
            "        Terms; or 2, behavior of abusing Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        Upon termination of the Terms for any reason, User shall destroy and remove\n" +
            "        from all computers, and other storage software, hardware, media, or printed\n" +
            "        copies of any Intellectual Property owned by Wormhole Wallet that the User\n" +
            "        acquired via use of Wormhole Wallet. The User’s representations in the\n" +
            "        Terms and any other provision of this Terms which by their nature are\n" +
            "        designed to survive termination shall survive termination or expiration of\n" +
            "        the Terms.\n" +
            "    </p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">XIII </strong><strong data-v-b1240d9e=\"\">Miscellaneous</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. You shall understand and fully comply with all applicable laws,\n" +
            "        regulations and rules that are related to the use of Wormhole Wallet in\n" +
            "        your jurisdiction.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. You may not assign or transfer any of rights, duties, and obligations\n" +
            "        contained in this Terms without prior written consent of Wormhole Wallet.\n" +
            "        Wormhole Wallet may assign or transfer any or all of its rights, duties and\n" +
            "        obligations contained in this Terms, in whole or in part, without obtaining\n" +
            "        your consent or approval.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. In use of Wormhole Wallet, you may contact us by submitting your\n" +
            "        feedback on Wormhole Wallet website or other means provided by us if you\n" +
            "        meet any problem.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4. Wormhole Wallet reserves the right to amend or modify any portion of\n" +
            "        this Terms at any time by publishing the revised version of the Terms on\n" +
            "        the website/application or by emailing to you the revised Terms. The\n" +
            "        revised Terms shall be effective immediately upon posting on the\n" +
            "        website/application or upon receipt of the email with the revised Terms.\n" +
            "        The Terms shall be deemed accepted by the User the first time the User uses\n" +
            "        Wormhole Wallet after the publishing of the revised Terms and shall apply\n" +
            "        prospectively with respect to any activity initiated after the publishing.\n" +
            "        If you do not agree with such amendments or modifications, your sole and\n" +
            "        exclusive remedy is to terminate your use of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5. You can find this Terms on Wormhole Wallet website, and you undertake to\n" +
            "        read the entire Terms carefully before using the website or any of the\n" +
            "        Functions provided by Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6. Wormhole Wallet’s failure or delay in exercising any right, power,\n" +
            "        privilege, or remedy under this Terms shall not operate as a waiver\n" +
            "        thereof. The single or partial exercise of any right, power, privilege, or\n" +
            "        remedy by Wormhole Wallet does not prevent either from exercising any other\n" +
            "        right, power, privilege, or remedy.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7. Limitation of compensation and liability for losses and damages.\n" +
            "        Wormhole Wallet is not liable for any damages or losses related to your use\n" +
            "        of Wormhole Wallet. In no event should Wormhole Wallet become obligated to\n" +
            "        be involved in disputes between users, or between users and any third party\n" +
            "        relating to the use of Wormhole Wallet. If you have a dispute with one or\n" +
            "        more Users of Wormhole Wallet, you release Wormhole Wallet, its affiliates\n" +
            "        and service providers, and each of their respective officers, directors,\n" +
            "        joint venturers, employees, and representatives from any and all claims,\n" +
            "        demands, and damages (actual or consequential) of every kind and nature\n" +
            "        arising out of or in any way connected with such disputes.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        We make no representation or warrants as to the adequacy, timeliness,\n" +
            "        accuracy, or completeness of any information or any website linked through\n" +
            "        hyperlink on the website and mobile applications of Wormhole Wallet,\n" +
            "        including but not limited to: the information of the projects launched by\n" +
            "        users through Wormhole Wallet’s platform. We do not endorse any information\n" +
            "        or data Users submitted to us. When you use Wormhole Wallet, you release\n" +
            "        Wormhole Wallet from claims, damages, and demands of every kind, known or\n" +
            "        unknown, suspected or unsuspected, disclosed or undisclosed, arising out of\n" +
            "        or in any way related to such disputes and your use of Wormhole Wallet. All\n" +
            "        information and data you access through Wormhole Wallet is at your own\n" +
            "        risk. You are solely responsible for any resulting damages or loss to any\n" +
            "        party.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        To the fullest extent permitted by law, in no event will Wormhole Wallet,\n" +
            "        its directors, employees, partners, suppliers, or content providers be\n" +
            "        liable for any indirect, incidental, punitive, consequential, special, or\n" +
            "        exemplary damages of any kind, including but not limited to damages (i)\n" +
            "        resulting from your access to, use of, or inability to access or use\n" +
            "        Wormhole Wallet; (ii) for any lost profits, data loss, or cost of\n" +
            "        procurement or substitute goods or services; or (iii) for any conduct of\n" +
            "        third party or content published by third party. In no event shall Wormhole\n" +
            "        Wallet’s liability for direct damages be in excess of (in the aggregate):\n" +
            "        a) the market value of 0.1 ETH; or b) 10 Singapore Dollars.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8. Tax\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) The fee incurred by using Wormhole Wallet shall be exclusive of all\n" +
            "        taxes that are applicable to, arising from, or in connection to your use of\n" +
            "        Wormhole Wallet in any jurisdiction (“Payable Tax”).\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) You shall be responsible for determining any Payable Tax and declaring,\n" +
            "        withholding, collecting, reporting and remitting the correct amount of\n" +
            "        Payable Tax to the appropriate tax authorities. You shall be solely liable\n" +
            "        for all penalties, claims, fines, punishments, or other liabilities arising\n" +
            "        from the non-fulfilment or non-performance to any extent of any of your\n" +
            "        obligations in relation to the Payable Tax.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        c) We shall not be responsible for determining any Payable Tax and\n" +
            "        declaring, withholding, collecting, reporting and remitting the correct\n" +
            "        amount of Payable Tax to the appropriate tax authorities.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        9. Severance and Partial Invalidity：\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        a) If any of part of this Terms is rendered void, illegal or unenforceable\n" +
            "        by any legislation to which it is subject to, it shall be rendered void,\n" +
            "        illegal or unenforceable to that extent and no further and, for the\n" +
            "        avoidance of doubt, the rest of this Terms shall continue to be valid and\n" +
            "        in full force and effect.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        b) The illegality, invalidity or unenforceability of any provision of this\n" +
            "        Terms under the law of any jurisdiction shall not affect its legality,\n" +
            "        validity or enforceability under the law of any other jurisdiction nor the\n" +
            "        legality, validity or enforceability of any other provision.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        10. This Terms shall become effective on November 5, 2018.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        11. As for any issues not covered in this Terms, you shall comply with the\n" +
            "        announcements and relevant rules as updated by Wormhole Wallet from time to\n" +
            "        time.\n" +
            "        <br data-v-b1240d9e=\"\" clear=\"all\"></p><p data-v-b1240d9e=\"\" align=\"center\"><strong data-v-b1240d9e=\"\">Wormhole Wallet Privacy Policy</strong></p><p data-v-b1240d9e=\"\" align=\"center\"><strong data-v-b1240d9e=\"\"></strong></p><p data-v-b1240d9e=\"\">\n" +
            "        Last Updated: November 5, 2018.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n";
                    String term3 =
            "        Dear users,\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        Satoshi Foundation Ltd. (“Wormhole Wallet”, “we”, “us”, or “our”) respects\n" +
            "        and protects the privacy of users (“you”, “your” or “Users”). Wormhole\n" +
            "        Wallet will collect, use, disclose and process your Personal Information,\n" +
            "        in accordance with this Privacy Policy (“Policy”) when you:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        (a) access or use our website and mobile applications, and its associated\n" +
            "        functions (“Functions”); and/or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        (b) provide us with your Personal Information, regardless or the medium\n" +
            "        through which such Personal Information is provided.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        Wormhole Wallet recommends that you shall carefully read and understand the\n" +
            "        whole contents of this Policy before your use of Wormhole Wallet.\n" +
            "        Additionally, significant information is in bold form in this Policy.\n" +
            "        <strong data-v-b1240d9e=\"\">\n" +
            "            By providing us with your Personal Information, you consent to our\n" +
            "            collection, use, disclosure (including transfer) and processing of your\n" +
            "            Personal Information in accordance with this Policy.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\"><strong data-v-b1240d9e=\"\">\n" +
            "            Please DO NOT provide any Personal Information to us if you do not\n" +
            "            accept this Policy.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        Wormhole Wallet reserves the right to update this Policy online from time\n" +
            "        to time, without notice to you, and the revised Policy will come into\n" +
            "        effect and supersede the older versions once posted on our Website or\n" +
            "        application. The revised Policy will apply to Personal Information provided\n" +
            "        to us previously.\n" +
            "        <strong data-v-b1240d9e=\"\">\n" +
            "            In particular, if you do not accept the revised Policy, please\n" +
            "            immediately stop your use of Wormhole Wallet. Your continuous use of\n" +
            "            Wormhole Wallet will be regarded as your acceptance of the revised\n" +
            "            policy.\n" +
            "        </strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1. <strong data-v-b1240d9e=\"\">Information We Collect</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        1.1 We collect your Personal Information, including but not limited to your\n" +
            "        mobile device information, operation records, transaction records and\n" +
            "        wallet addresses.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.2 You confirm that your Wallet Password, Private Key, Mnemonic Words on\n" +
            "        Wormhole Wallet are not stored or synchronized on the Wormhole Wallet’\n" +
            "        servers. The Wormhole Wallet does not offer the service to recover your\n" +
            "        Wallet Password, Private Key, Mnemonic Words.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.3 We may also request you to provide us with additional Personal\n" +
            "        Information in order for us to enable your use of any specific function of\n" +
            "        Wormhole Wallet. Your refusal to provide us with the requested Personal\n" +
            "        Information will be considered as your choice to not use a particular\n" +
            "        specific function of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4 To the extent permitted by applicable laws and regulations, Wormhole\n" +
            "        Wallet may collect and use the Personal Information in the following\n" +
            "        circumstances without your prior consent or authorization:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.1 information related to national security and national defense;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.2 information related to public security, public health, significant\n" +
            "        public interests;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.3 information related to criminal investigation, prosecution, trial and\n" +
            "        enforcement;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.4 Personal Information in the public domain;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.5 Personal Information collected from legally publicly disclosed\n" +
            "        information, such as legal news reports, government information disclosure\n" +
            "        and other channels;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.6 Personal Information necessary to maintain the security and\n" +
            "        compliance of Wormhole Wallet, such as to detect or to solve the\n" +
            "        malfunction of Wormhole Wallet; and/or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.4.7 other circumstances permitted by laws and regulations.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5 We collect information in the following ways:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.1 when you provide us with your Personal Information for whatever\n" +
            "        reasons;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.2 when you authorize us to obtain your Personal Information from a\n" +
            "        third party;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.3 when you register a user account at Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.4 when you contact us or interact with our employees through various\n" +
            "        communication channels, for example, through social media platforms,\n" +
            "        messenger platforms, face-to-face meetings, telephone calls, emails, fax\n" +
            "        and letters;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.5 when you transact with us, contact us or request us to contact you;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.6 when you request to be included in an email or our mailing list;\n" +
            "        and/or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.7 when we copy all or part of your transaction records on the\n" +
            "        Blockchain. However, you should refer to the Blockchain system for your\n" +
            "        latest transaction records.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.5.8 Wormhole Wallet may contain certain technologies that collect\n" +
            "        Personal Information in the manner described in this Policy (see paragraph\n" +
            "        5 below) or the applicable terms and conditions.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.6 Your provision of Personal Information to us is voluntary and you may\n" +
            "        withdraw your consent for us to use your Personal Information at any time.\n" +
            "        However, if you choose not to provide us with the Personal Information we\n" +
            "        require, it may not be possible for you to use Wormhole Wallet or for us to\n" +
            "        contact you or provide certain function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.7 In certain circumstances, you may also provide us with the Personal\n" +
            "        Information of persons other than yourself. If you do so, you warrant that\n" +
            "        you have informed him/her of the purposes for which we are collecting\n" +
            "        his/her Personal Information and that he/she has consented to your\n" +
            "        disclosure of his/her Personal Information to us for those purposes. You\n" +
            "        agree to indemnify and hold us harmless from and against any and all claims\n" +
            "        by such individuals relating to our collection, use and disclosure of such\n" +
            "        Personal Information in accordance with the terms of this Policy.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        1.8 You are responsible for ensuring that all Personal Information that you\n" +
            "        provide to us is truthful, accurate and complete. You are responsible for\n" +
            "        informing us of any changes to your Personal Information.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2. <strong data-v-b1240d9e=\"\">How We Use Your Information</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        How do we use or disclose your Personal Information?\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1 We collect, use or disclose your Personal Information for one or more\n" +
            "        of the following purposes:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.1 to provide you with certain functions of Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.2 to manage your relationship with us;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.3 to facilitate your use of Wormhole Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.4 to push important notifications to you, such as software update,\n" +
            "        update of Terms and this Policy;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.5 to assist with your enquiries, feedback, complaints and requests by\n" +
            "        using the wallet address and the mobile device information provided by you;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.6 to notify you of information about Wormhole Wallet and relevant\n" +
            "        products, programs and events;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.7 to resolve any disputes, investigating any complaint, claim or\n" +
            "        dispute or any actual or suspected illegal or unlawful conduct;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.8 to conduct our internal audit, data analysis and research;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.9 to conduct user behavior tracking by tracking Users’ use of Wormhole\n" +
            "        Wallet;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.10 to comply with our obligations in accordance with laws, regulations\n" +
            "        and to cooperate with regulatory authorities;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.11 to comply with international sanctions and applicable regulation for\n" +
            "        securities and to counter money-laundering or financing of terrorism;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.12 to enforce obligations owed to us, and contractual terms and\n" +
            "        conditions; and/or\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.1.13 any other reasonable purposes related to the aforementioned.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.2 We may use your Personal Information, from time to time, for additional\n" +
            "        purposes such as to inform you of the latest activities, special offers and\n" +
            "        promotions offered by our strategic business partners, associates and\n" +
            "        affiliates, if you consent to such use.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        2.3 We offer you the “Touch ID” option to provide you with a convenient way\n" +
            "        to use Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3. <strong data-v-b1240d9e=\"\">How You Control Your Own Information</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        You are entitled to control your Personal Information provided to Wormhole\n" +
            "        Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.1 You may import your other wallets into Wormhole Wallet system through\n" +
            "        synchronization of wallets and you may export your wallets from Wormhole\n" +
            "        Wallet system to other token management wallets. Wormhole Wallet will\n" +
            "        display the information of imported wallets to you.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.2 You acknowledge that since Blockchain is an open source system, your\n" +
            "        transaction records are automatically public and transparent in the whole\n" +
            "        Blockchain.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.3 Wormhole Wallet may contain links to other websites, applications or\n" +
            "        smart contracts that are not owned, operated, developed or maintained by\n" +
            "        us. These links are provided only for your convenience. This Policy only\n" +
            "        applies to Wormhole Wallet and its Functions. When visiting these\n" +
            "        third-party websites, or using these third-party applications or Smart\n" +
            "        Contracts, you understand that the Wormhole Wallet Terms and Wormhole\n" +
            "        Wallet Privacy Policy will no longer apply. You are encouraged to carefully\n" +
            "        review their privacy policies and related terms.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.4 You are entitled to ask us to update, revise, and delete your Personal\n" +
            "        Information and/or withdraw any consent provided to us. If you wish to\n" +
            "        withdraw any consent you have given us at any time, or if you wish to\n" +
            "        update, revise, delete or have access to your Personal Information held by\n" +
            "        us, or if you do not accept any amendment to this Policy, please contact us\n" +
            "        at:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        Email: support@wormhole.cash\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.5 We may charge you a fee for responding to your request for access to\n" +
            "        your Personal Information held by us, or for information about the ways in\n" +
            "        which we have (or may have) used your Personal Information in the one-year\n" +
            "        period preceding your request. If a fee is to be charged, we will inform\n" +
            "        you of the amount beforehand and respond to your request after payment is\n" +
            "        received. We will endeavor to respond to your request within a reasonable\n" +
            "        amount of time.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        3.6 In many circumstances, we need to use your Personal Information in\n" +
            "        order for us to provide you with certain function. If you do not provide us\n" +
            "        with the required Personal Information, or if you withdraw your consent to\n" +
            "        our use and/or disclosure of your Personal Information for these purposes,\n" +
            "        it may not be possible for us to continue to provide you with the certain\n" +
            "        function.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.<strong data-v-b1240d9e=\"\">Information We may Share or Transfer</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        4.1 We will keep your Personal Information for so long as we need the\n" +
            "        Personal Information for our legitimate purposes.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.2 We do not sell, trade or otherwise transfer your Personal Information\n" +
            "        to third parties without your consent.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.3 If you have consented to our disclosure of your Personal Information to\n" +
            "        our strategic business partners and associates, we may disclose your\n" +
            "        Personal Information to them. They will use your Personal Information only\n" +
            "        for the purposes you have consented to.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.4 You agree that we may disclose or share your Personal Information with\n" +
            "        third parties such as:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.4.1 service providers and data processors working on our behalf and\n" +
            "        providing services to us such as conducting know-your-clients checks,\n" +
            "        accounting, data processing or management services, website hosting,\n" +
            "        maintenance and operation services, e-mail message services, analysis\n" +
            "        services, handling of payment transactions, marketing; and\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.4.2 our consultants and professional advisors (such as accountants,\n" +
            "        lawyers, auditors).\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.5 If we need to transfer your Personal Information to any country for the\n" +
            "        purposes set out above, we shall obtain your prior consent and ensure that\n" +
            "        the recipient of the Personal Information protects your Personal\n" +
            "        Information to the same level as we have committed to protecting your\n" +
            "        Personal Information. Where these countries or territories do not have\n" +
            "        personal data protection laws that are comparable to the laws applicable to\n" +
            "        our relationship with you, we will enter into legally enforceable\n" +
            "        agreements with the recipients.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.6 Wormhole Wallet will not share with or transfer your Personal\n" +
            "        Information to any third party without your prior consent, except for the\n" +
            "        following circumstances:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.6.1 the collected Personal Information is publicized by yourself;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.6.2 the Personal Information is collected from public information which\n" +
            "        was legally disclosed, such as news (lawfully reported), government\n" +
            "        information disclosure and other channels;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.6.3 in order to abide by applicable laws, regulations, legal procedures,\n" +
            "        and administrative or judiciary authorities or to enforce our Policy or\n" +
            "        protect our or others’ rights, property or safety;\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        4.6.4 in the case of mergers and acquisitions, if transfer of Personal\n" +
            "        Information is involved, the Wormhole Wallet may require the receivers of\n" +
            "        Personal Information to be continuously bound by this Policy.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5.<strong data-v-b1240d9e=\"\">Automatic Data Collection Technologies</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        5.1 We use Automatic Data Collection Technologies on our Functions.\n" +
            "        Examples of such technologies include:\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5.1.1 Cookies (or browser cookies). Cookies are small text files which are\n" +
            "        set by a website or application operator so that your browser or device may\n" +
            "        be recognized. We may make use of cookies on our website to store and track\n" +
            "        information such as the number of users and their frequency of use,\n" +
            "        profiles of users and their online preferences. Cookies do not capture\n" +
            "        information which would personally identify you, but the information\n" +
            "        collected may be used to assist us in analyzing the usage of Wormhole\n" +
            "        Wallet and to improve your online experience with us. You can disable the\n" +
            "        cookies by changing the setting on your browser. However, this may affect\n" +
            "        your use of certain functions of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        5.1.2 Web analytics. Web analytics is the term given to a method for\n" +
            "        collecting and assessing the behavior of visitors to websites and mobile\n" +
            "        applications. This includes the analysis of traffic patterns in order, for\n" +
            "        example, to determine the frequency of visits to certain parts of a website\n" +
            "        or mobile application, or to find out what information and functions our\n" +
            "        visitors are most interested in. The web analytics services at Wormhole\n" +
            "        Wallet are provided by third-party service providers.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6.<strong data-v-b1240d9e=\"\">How We Protect Your Information</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        6.1 If Wormhole Wallet system ceases operation, Wormhole Wallet will stop\n" +
            "        the collection of your Personal Information and take steps to delete or\n" +
            "        anonymize your Personal Information held by us within a reasonable period.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        6.2 To protect your Personal Information, Wormhole Wallet may adopt data\n" +
            "        security techniques, improve internal compliance levels, provide security\n" +
            "        training for our staff, and set security authority for access to relevant\n" +
            "        data to protect your Personal Information.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7.<strong data-v-b1240d9e=\"\">Disclaimer</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        7.1 After you access the websites, applications or smart contracts operated\n" +
            "        by third parties, you acknowledge that this Policy no longer applies to the\n" +
            "        collection, use, disclosure and transfer of your Personal Information by\n" +
            "        these third parties. Wormhole Wallet is unable to guarantee that these\n" +
            "        third-party websites, applications and/or smart contracts will implement\n" +
            "        reasonable security measures to protect your Personal Information.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7.2 You are solely responsible for your use of these third-party websites,\n" +
            "        applications and/or smart contracts and agree that you will not hold\n" +
            "        Wormhole Wallet liable for any damages incurred or injuries inflicted as a\n" +
            "        result of the collection, use, disclosure and transfer of your Personal\n" +
            "        Information by these third-party websites, applications and/or Smart\n" +
            "        Contracts.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        7.3 YOU ACKNOWLEDGE AND ACCEPT THAT, TO THE MAXIMUM EXTENT PERMITTED BY\n" +
            "        APPLICABLE LAW, WORMHOLE WALLET WILL ADOPT MEASURES AS REASONABLE AS\n" +
            "        POSSIBLE TO PROTECT YOUR PERSONAL INFORMATION UNDER CURRENT TECHNIQUES ON\n" +
            "        AN “AS IS”, “AS AVAILABLE” AND “WITH ALL FAULTS” BASIS, TO AVOID THE\n" +
            "        DISCLOSURE, TAMPERING OR DAMAGE OF INFORMATION. SINCE WORMHOLE WALLET\n" +
            "        TRANSFERS DATA WIRELESSLY, WORMHOLE WALLET MAKES NO GUARANTEE ON THE\n" +
            "        PRIVACY AND SECURITY OF WIRELESS INTERNET DATA TRANSFERRING.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8.<strong data-v-b1240d9e=\"\">Miscellaneous</strong></p><p data-v-b1240d9e=\"\">\n" +
            "        8.1 You shall fully understand and conform to the laws, regulations and\n" +
            "        rules in your jurisdictions which are relevant to use of Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8.2 The validity, interpretation, alternation, enforcement, dispute\n" +
            "        resolution of this Policy and its revised versions shall be governed and\n" +
            "        construed in accordance with the laws of Singapore. Where there is no\n" +
            "        applicable law, this Policy shall be interpreted by applicable commercial\n" +
            "        and/or industrial practices. If any dispute or claim in connection with\n" +
            "        this Policy arises between you and Wormhole Wallet, the parties shall first\n" +
            "        attempt to resolve the dispute or claim through amicable negotiations in\n" +
            "        good faith. If the parties cannot reach an agreement, both parties agree to\n" +
            "        submit such dispute or claim to arbitration. The arbitration shall be\n" +
            "        submitted to Singapore International Arbitration Center (“SIAC”) in\n" +
            "        accordance with SIAC Rules (“SIAC Rules”) in effect before one arbitrator\n" +
            "        to be appointed according to the SIAC Rules. The decision and awards of the\n" +
            "        arbitration shall be final and binding upon the parties hereto.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8.3 You may access this Policy and other terms (e.g. Wormhole Wallet Terms)\n" +
            "        through Wormhole Wallet. We encourage you to check Wormhole Wallet Terms\n" +
            "        and Wormhole Wallet Privacy Policy each time you use Wormhole Wallet.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8.4 Any translated versions of this Policy are provided for the convenience\n" +
            "        of Users, and are not intended to amend the original English version of\n" +
            "        this Policy. If there is any discrepancy between the English version and\n" +
            "        non-English version of this Policy, the English version shall prevail.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        8.5 This Policy shall become effective on November 5, 2018.\n" +
            "    </p><p data-v-b1240d9e=\"\">\n" +
            "        As for any issues not covered in this Policy, you shall comply with the\n" +
            "        announcements and relevant rules as updated by the Wormhole Wallet from\n" +
            "        time to time.\n" +
            "    </p><p data-v-b1240d9e=\"\" align=\"right\">\n" +
            "        Satoshi Foundation\n" +
            "    </p></div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        setTilte(getString(R.string.priviva_title));
        bindViews();
    }

    private void bindViews() {
        ((TextView)findViewById(R.id.tv)).setText(Html.fromHtml(terms  + term2 + term3));
    }


}
