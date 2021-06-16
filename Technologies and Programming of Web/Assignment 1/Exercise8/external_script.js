function func_index() {
    document.querySelector("#head1").innerHTML='What is Machine Learning';
    document.querySelector("#h2_1").innerHTML='Definition';
    document.querySelector("#h2_2").innerHTML='What does Machine Learning deal with?';
    document.querySelector("#h2_3").innerHTML='What other branches is it connected with?';
    document.querySelector("#p_1").innerHTML='Machine learning is a subfield of computer science developed from the study of pattern recognition and computational learning theory in artificial intelligence.';
    document.querySelector("#p_2").innerHTML='Machine learning explores the study and construction of algorithms that can learn from data and make predictions about it.';
    document.querySelector("#p_2_1").innerHTML='Such algorithms work by constructing models from experimental data in order to make data-based predictions or to make decisions expressed as a result.';
    document.querySelector("#p_3").innerHTML='Machine learning is closely related and often confused with computational statistics, a branch that also focuses on prediction through the use of computers.';
    document.querySelector("#p_3_1").innerHTML='It has strong links with mathematical optimization, which provides it with methods, theory and areas of application.';
    document.querySelector("#h3_1").innerHTML='Links for more information';
    document.querySelector("#nav_1").innerHTML='Front Page';
    document.querySelector("#nav_2").innerHTML='What is Machine Learning';
    document.querySelector("#nav_3").innerHTML='Objects & Practices';
    document.querySelector("#nav_4").innerHTML='Personal Experience';
    document.querySelector("#nav_5").innerHTML='Change Theme';
    document.querySelector("#nav_6").innerHTML='Sign Up';
    document.querySelector("#fig_1").innerHTML='The field of Machine Learning has many practices in everyday life.';
}

function func_page2() {
    document.querySelector("#head1").innerHTML='Objects & Practices';
    document.querySelector("#h2_1").innerHTML='Where the theory of Machine Learning is applied';
    document.querySelector("#h2_2").innerHTML='Types of problems and tasks';
    document.querySelector("#p_1").innerHTML='Machine learning is applied in a series of computational tasks, where both the design and the explicit programming of the algorithms are impossible. Examples of applications are spam filters, optical character recognition (OCR), search engines, and computational vision.';
    document.querySelector("#h3_1").innerHTML='Links for more information';
    document.querySelector("#li_1").innerHTML='Supervised learning: The computer program receives exemplary inputs as well as the desired results from a "teacher", and the goal is to learn a general rule in order to match the inputs with the results.';
    document.querySelector("#li_2").innerHTML='Unsupervised learning: Without providing any experience to the learning algorithm, it must find the structure of the input data. Unsupervised learning can be an end in itself (discovering hidden patterns in data) or a means to an end (characteristic of learning).';
    document.querySelector("#li_3").innerHTML='Reinforcement learning: A computer program interacts with a dynamic environment in which a specific goal must be achieved (such as driving a vehicle), without a teacher explicitly telling him if he has come close to his goal. Another example is learning to play a game against an opponent.';
    document.querySelector("#nav_1").innerHTML='Front Page';
    document.querySelector("#nav_2").innerHTML='What is Machine Learning';
    document.querySelector("#nav_3").innerHTML='Objects & Practices';
    document.querySelector("#nav_4").innerHTML='Personal Experience';
    document.querySelector("#nav_5").innerHTML='Change Theme';
    document.querySelector("#nav_6").innerHTML='Sign Up';
    document.querySelector("#fig_1").innerHTML='Machine Learning Practices in various fields.';
}


function func_page3() {
    document.querySelector("#head1").innerHTML='Personal Experience';
    document.querySelector("#nav_1").innerHTML='Front Page';
    document.querySelector("#nav_2").innerHTML='What is Machine Learning';
    document.querySelector("#nav_3").innerHTML='Objects & Practices';
    document.querySelector("#nav_4").innerHTML='Personal Experience';
    document.querySelector("#nav_5").innerHTML='Change Theme';
    document.querySelector("#nav_6").innerHTML='Sign Up';
    document.querySelector("#h2_1").innerHTML='From the point of view of the author';
    document.querySelector("#h3_1").innerHTML='Contact info';
    document.querySelector("#p_1").innerHTML='Machine Learning is a very demanding but at the same time interesting subject of Computer Science.';
    document.querySelector("#p_2").innerHTML='Personally, I have been involved in the Artificial Intelligence course, where basic concepts and practices have been introduced. We were even assigned a task based on supervised learning to rate critics as positive or negative. I got a positive impression from this work, as it offered me important knowledge despite the increased difficulty it had.';
    document.querySelector("#p_3").innerHTML='I chose in the current semester to attend the corresponding course taught in our school, in order to get in touch with this subject in more depth.';
    document.querySelector("#p_4").innerHTML='Machine Learning is widely used and its practices are perceptible in the daily life of all of us and it is an industry that will never stop growing. For this reason, I would like to deal professionally with this subject in the future.';
    document.querySelector("#p_5").innerHTML='AUEB email address: p3170160@aueb.gr';
    document.querySelector("#p_6").innerHTML='Personal email address: tsl.aggelos@gmail.com';
    document.querySelector("#fig_1").innerHTML='Machine Learning, one of the most promising areas of Computer Science.';

}

var d = new Date();

function func_Theme_index() {
    document.body.style.backgroundImage = "url('https://velocityglobal.com/wp-content/uploads/2019/02/Blog-Images-Forget-Machine-Learning-Humans-Still-Have-a-Lot-to-Learn-Part-II.jpg')";
    document.getElementById('img_1').style.display = 'none';
    document.getElementById('fig_1').innerHTML='';
}

function func_Theme_page2() {
    document.body.style.backgroundImage = "url('https://velocityglobal.com/wp-content/uploads/2019/02/Blog-Images-Forget-Machine-Learning-Humans-Still-Have-a-Lot-to-Learn-Part-II.jpg')";
    document.getElementById('img_2').style.display = 'none';
    document.getElementById('fig_1').innerHTML='';
}

function func_Theme_page3() {
    document.body.style.backgroundImage = "url('https://velocityglobal.com/wp-content/uploads/2019/02/Blog-Images-Forget-Machine-Learning-Humans-Still-Have-a-Lot-to-Learn-Part-II.jpg')";
    document.getElementById('img_3').style.display = 'none';
    document.getElementById('fig_1').innerHTML='';
}


function func_Theme_form() {
    document.body.style.backgroundImage = "url('https://velocityglobal.com/wp-content/uploads/2019/02/Blog-Images-Forget-Machine-Learning-Humans-Still-Have-a-Lot-to-Learn-Part-II.jpg')";
    document.getElementById('img_4').style.display = 'none';
}

function func_Theme_question() {
    document.body.style.backgroundImage = "url('https://velocityglobal.com/wp-content/uploads/2019/02/Blog-Images-Forget-Machine-Learning-Humans-Still-Have-a-Lot-to-Learn-Part-II.jpg')";
    document.getElementById('img_5').style.display = 'none';
}





if(d.getDay() == 1){
    document.body.style.backgroundColor = "rgb(255, 218, 203)";
}
else if(d.getDay()== 2){
    document.body.style.backgroundColor = "rgb(238, 255, 210)";

}
else if(d.getDay()== 3){
    document.body.style.backgroundColor = "rgb(203, 249, 255)";

}
else if(d.getDay()== 4){
    document.body.style.fontFamily = 'Times, Times New Roman', Georgia, serif;

}
else if(d.getDay()== 5){
    document.getElementById("img_1").src = "images/ml4_edited.jpeg";

}

else if(d.getDay()== 6){
    document.getElementById("img_1").src = "images/ml5_edited.jpg";

}
else if(d.getDay()== 7){
    document.getElementById("img_1").src = "images/ml6_edited.png";

}

/* Constraints */

function validations() {

    //Validate Password
    var pass = document.getElementById("pass_word_og");
    var passConf = document.getElementById("pass_word_conf");

    pass.addEventListener('input', () => {
        pass.setCustomValidity('');
        pass.checkValidity();
      });

      pass.addEventListener('invalid', () => {
        if(pass.value  == '' ) {
            pass.setCustomValidity('Παρακαλώ πληκτρολογήστε τον κωδικό σας');
        }
      });

      passConf.addEventListener('input', () => {
        passConf.setCustomValidity('');
        passConf.checkValidity();
      });

      passConf.addEventListener('invalid', () => {
        if(passConf.value  == '' ) {
            passConf.setCustomValidity('Παρακαλώ επιβεβαιώστε τον κωδικό σας');
        }
      });

    if(pass.value != passConf.value){
        passConf.setCustomValidity('Οι κωδικοί δεν ταιριάζουν. Παρακαλώ πληκτρολογήστε τους σωστά.');
    }


    //Validate first name-only letters

    const first_name = document.getElementById('first_name');

    first_name.addEventListener('input', () => {
    first_name.setCustomValidity('');
    first_name.checkValidity();
    });
      
    first_name.addEventListener('invalid', () => {
      if(first_name.value === '') {
        first_name.setCustomValidity('Παρακαλώ πληκτρολογήστε το όνομα σας!');
      } else {
        first_name.setCustomValidity('Παρακαλώ πληκτρολογήστε το όνομα σας με ελληνικούς ή λατινικούς χαρακτήρες.');
      }
    });

          //Validate last name-only letters allowed

      const last_name = document.getElementById("last_name");
      
      last_name.addEventListener('input', () => {
        last_name.setCustomValidity('');
        last_name.checkValidity();
      });
      
      last_name.addEventListener('invalid', () => {
        if(last_name.value === '') {
            last_name.setCustomValidity('Παρακαλώ πληκτρολογήστε το επίθετο σας!');
        } else {
            last_name.setCustomValidity('Παρακαλώ πληκτρολογήστε το επίθετο σας με ελληνικούς ή λατινικούς χαρακτήρες.');
        }
      });

          //Validate state-only letters allowed


      const state = document.getElementById("inputState");
      
      state.addEventListener('input', () => {
        state.setCustomValidity('');
        state.checkValidity();
      });
      
      state.addEventListener('invalid', () => {
        if(state.value === '') {
            state.setCustomValidity('Παρακαλώ πληκτρολογήστε τον νομό σας!');
        } else {
            state.setCustomValidity('Παρακαλώ πληκτρολογήστε το νομό σας με ελληνικούς ή λατινικούς χαρακτήρες.');
        }
      });

          //Validate city-only letters allowed

      const city = document.getElementById("inputCity");
      
      city.addEventListener('input', () => {
        city.setCustomValidity('');
        city.checkValidity();
      });
      
      city.addEventListener('invalid', () => {
        if(city.value === '') {
            city.setCustomValidity('Παρακαλώ πληκτρολογήστε την πόλη σας!');
        } else {
            city.setCustomValidity('Παρακαλώ πληκτρολογήστε την πόλη σας με ελληνικούς ή λατινικούς χαρακτήρες.');
        }
      });

      //Validate phone number

      const phone = document.getElementById("mobileNumber");

      phone.addEventListener('input', () => {
        phone.setCustomValidity('');
        phone.checkValidity();
      });
      
      phone.addEventListener('invalid', () => {
        if(phone.value === '') {
          phone.setCustomValidity('Παρακαλώ πληκτρολογήστε τον αριθμό του κινητου σας τηλεφώνου!');
        } else {
          phone.setCustomValidity('Παρακαλώ πληκτρολογήστε έναν έγκυρο αριθμό κινητού τηλεφώνου.');
        }
      });

      //Validate Age-only over 17.5 years old allowed

      var birthday=document.getElementById("given_birthday")
      var birth=new Date(birthday.value);

      if((d.getFullYear() - birth.getFullYear()) < 18){
        birthday.setCustomValidity("Πρέπει να είστε άνω των 18 για να εγγραφείτε!");
      }else{
        birthday.setCustomValidity("");
      }
  }