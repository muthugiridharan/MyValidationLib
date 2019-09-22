
# MyValidationLib [![](https://jitpack.io/v/muthugiridharan/MyValidationLib.svg)](https://jitpack.io/#muthugiridharan/MyValidationLib) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/c4073409479f43779647d9f17210133b)](https://www.codacy.com/manual/muthugiridharan/MyValidationLib?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=muthugiridharan/MyValidationLib&amp;utm_campaign=Badge_Grade)

A small validation library to make your life easy.

## Validation Features

 - For Email
> @Parm1 ->Your View (Text Input Layout, Edit Text)<br>
> @Parm2 ->Your ErrorMessage<br>

    myValidator.checkEmail(view,error_message);
    
 - For Name
> @Parm1 ->Your View (Text Input Layout, Edit Text)<br>
> @Parm2 ->Your ErrorMessage<br>

    myValidator.checkName(view,error_message);

 - For Non Empty Feilds

> @Parm1 ->Your View (Text Input Layout, Edit Text)<br>
> @Parm2 ->Your ErrorMessage<br>

    myValidator.checkNotEmpty(view,error_message);

 - For Password
> @Parm1 -> View (Text Input Layout, Edit Text)<br>
> @Parm2 -> Password Type<br>
> @Parm3-> Error Message<br>
**Password Type**<br>
|**basic_password**| Minimum 8 characters  with one upper case and lower care |<br>
|**medium_password**| Minimum 8 character with one upper case,lower case and number|<br>
|**strong_password**| Minimum 8 character with one upper case, lower case, number and spl characters(#?!@$%^&*-)|

    myValidator.checkPassword(view,password_type,error_message);

>**Confirm Password**<br>
> @Parm1-> Primary View (Text Input Layout, Edit Text)<br>
> @Parm2-> Secondary View (Text Input Layout, Edit Text)<br>
> @Parm3-> Error Message<br>

    myValidator.checkConfirmPassword(primary_view,secondary_view,error_message);
    
 - Check Age
 
> @Parm1-> View (Text Input Layout, Edit Text)<br>
> @Parm2-> Date Format (Eg.- dd-MM-yyyy)<br>
> @Parm3-> Minimum Age<br>
> @Parm4-> Error Message<br>

    myValidator.checkDateOfBirth(view,date_format,error_messsage);
    

 - Single Selection
>
> @Parm1-> View (Radiobutton and Checkbox)<br>
> @Parm2-> Error Message<br>

 - Minimum and Maxmium character length

> @Parm1-> View (Text Input Layout, Edit Text)<br>
> @Parm2-> Minimum Character<br>
> @Parm3-> Maxmium Character<br>
> @Parm4-> Error Message<br>

    myValidator.checkMinMax(view,min_length,max_length,error_message);
    

 - Minumum character length
 

> @Parm1->View (Text Input Layout, Edit Text)<br>
> @Parm2-> Minimum Character<br>
> @Parm3-> Error Message<br>

    myValidator.checkMin(view,min_length,error_message);

 - Maximum character length

> @Parm1->View (Text Input Layout, Edit Text)<br>
> @Parm2-> Maximum Character<br>
> @Parm3-> Error Message<br>

    myValidator.checkMax(view,max_length,error_message);
    

 - Custom regex
> @Parm1-> View (Text Input Layout, Edit Text)<br>
> @Parm2-> Regex<br>
> @Parm3-> Error Message<br>

    myValidator.checkMax(view,max_length,error_message);

 - To check image

> Note : Use place holders in background of the image<br>
> @Parm1 -> View ( Image View )<br>
> @Parm2 ->Your ErrorMessage<br>

    myValidator.checkPicture(view,error_message);

## Sample Code

    private  MyValidator myValidator;
    class validation implements MyValidator.MyValidatorListener{ 
    //In OnCreate{
        myValidator = new MyValidator(activity);
        myValidator.setListener(this);
    
        //optional to display error message (true | false) by default it is true.
        myValidator.isSetErrorMessage(true);
    
        button.onClick{
		    //for Async
		    myValidator.doValidation(true);
		    //for Sync
		    myValidator.doValidation();
		    }
        setValidationRules();
	    }
    }
    
    @Override
    public void onSuccessValidation() {
    } 
      
    @Override
    public void onValidationError(List<ValidationResult> validationResultList) {
	    //Get Error view and error message here
	    for(ValidationResult validationResult : validationResultList){
		    //validationResult.view
		    //validationResult.errMsg
	    }
    }
    
    private void setValidationRules(){
	    myValidator.checkEmail(view,error_message);
	    .
	    .
	    .
	    myValidator.checkPicture(imageView,error_message);
    }
For further reference reffer base project [Main Activity](https://github.com/muthugiridharan/MyValidationLib/blob/master/app/src/main/java/com/giri/validationproject/MainActivity.java) 
 ## Screen Shots
![enter image description here](https://lh3.googleusercontent.com/764ArwSAqtLTg-7d2kBX4EWdwWJPREe7CT5GhIF4pFYMdMIso7Kl9Xw5HdExz8Q--iXwZTJl8ls)   
 ## How to use it
 **Gradle:**
    In build.gradle
```css
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
In dependency
```css
dependencies {
	        implementation 'com.github.muthugiridharan:MyValidationLib:Tag'
	}

```
**Maven**
```markup
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
```markup
	<dependency>
	    <groupId>com.github.muthugiridharan</groupId>
	    <artifactId>MyValidationLib</artifactId>
	    <version>Tag</version>
	</dependency>

```
## Licence
Copyright (c) 2019 Muthu Giridharan.See the [License](https://github.com/muthugiridharan/MyValidationLib/blob/master/LICENSE) file for license rights and limitations (MIT).
