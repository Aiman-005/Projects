//manager with account, password is manager12
#include<stdio.h>
#include<time.h>
#include<string.h>
#include<stdlib.h>
#include<stdbool.h>
#include<ctype.h>
struct Manager{
	char name[50];
	char pass[50];
	char email[50];
	char re_pass[50];
	int e_attempts;
};
#define max_hotels 5
#define lim 3
void  Destination_Hotel(int destinationChoice);
void CUSTOMER_DATA();
void Booking(char name[],const char *Airlines[3][3],int AirlinePrices[3][3]);
void TRAVEL_MANAGEMENT_FILE();
void bookOneWayFlight(char name[],const char *cities[],const char *Airlines[3][3],int AirlinePrices[3][3]);
void bookRoundTripFlight(char name[],const char *cities[],const char *Airlines[3][3],int AirlinePrices[3][3]);
void displayCities();
int displayAirlines(int budgetChoice);
int generateBillOneWay(int budgetChoice,int airlinechoice, int adults, int children);
int generateBillRoundTrip(int budgetChoice,int airlinechoice,int adults, int children);
void manager_signup(struct Manager M1);
void dashboard();
void View();
void Update(struct Manager M1);
void updateHotels();
void update_airlines(int AirlinePrices[3][3],const char *Airlines[3][3]);
void Remove(struct Manager M1);
void remove_hotels(const char *cities[5]);
void remove_airlines(int AirlinePrices[3][3],const char *Airlines[3][3]);
int registeredmanager(char *maname);
const char *cities[5]={"Karachi","Islamabad","Lahore","Gilgit-Baltistan","Kashmir"};
const int cityCount=5;
int discountedCost=0;
char name[50];
const char *Airlines[3][3]={{"PIA", "AirBlue", "SereneAir"},
{"PIA Business", "AirBlue Business","SereneAir Business"},
{"PIA First Class","AirBlue First Class","SereneAir First Class"}};

int AirlinePrices[3][3]={{8000,7000,9000},
{20000,15000,25000},
{25000,18000,30000}};

char *HotelsDescription[max_hotels][lim]={{"HOTEL A-BudgetFriendly-PKR 5000/night","HOTEL B-Mid-PKR 10000/night","HOTEL C-Luxury-PKR 16000/night"},
               {"HOTEL L-BudgetFriendly-PKR 6000/night","HOTEL M-Mid-PKR 9000/night","HOTEL N-luxury-PKR 14000/night"},
	           {"HOTEL X-BudgetFriendly-PKR 7000/night","HOTEL Y-Mid-PKR 11000/night","HOTEL Z-luxury-PKR 18000/night"},
               {"HOTEL D-BudgetFriendly-PKR 4000/night","HOTEL E-Mid-PKR 8000/night","HOTEL F-luxury-PKR 13000/night"},
	           {"HOTEL G-BudgetFriendly-PKR 4500/night","HOTEL H-Mid-PKR 9400/night","HOTEL I-luxury-PKR 14500/night"}
	        };

void displayCities()
{
    printf("\nAvailable Cities:\n");
    for (int i=0;i<cityCount;i++)
	{
        printf("%d. %s\n",i+1,cities[i]);
    }
}
int displayAirlines(int budgetChoice)
{
	int choice;
    printf("\nAvailable Airlines:\n");
    for(int i=0;i<3;i++)
    {
    	printf("%s \n",Airlines[budgetChoice-1][i]);
	}
    switch (budgetChoice)
	{
        case 1:
        	printf("\nthe prices of economy class are: ");
            printf("\n1.PIA Rs %d",AirlinePrices[0][0]);
            printf("\n2.AirBlue Rs %d",AirlinePrices[0][1]);
            printf("\n3.SereneAir Rs%d",AirlinePrices[0][2]);
            break;
        case 2:
        	printf("\nthe prices of Business class are: ");
        	printf("\n1.PIA Rs %d",AirlinePrices[1][0]);
        	printf("\n2.AirBlue Rs %d",AirlinePrices[1][1]);
        	printf("\n3.SereneAir Rs%d",AirlinePrices[1][2]);
            break;
        case 3:
        	printf("\nthe prices of First class are: ");
        	printf("\n1.PIA Rs %d",AirlinePrices[2][0]);
        	printf("\n2.AirBlue Rs %d",AirlinePrices[2][1]);
        	printf("\n3.SereneAir Rs%d",AirlinePrices[2][2]);
            break;
        default:
            printf("Invalid budget choice.\n");
            break;
    }

    printf("\nSelect the airline (enter 1, 2, or 3): ");
    scanf(" %d",&choice);
    fflush(stdin);
    return choice - 1;
}
void bookOneWayFlight(char name[],const char *cities[5],const char *Airlines[3][3],int AirlinePrices[3][3])
{
    int departureChoice,destinationChoice,budgetChoice,airlinechoice,adults,children,C_attempts=0;
    char date[20],copydate[20];
    FILE *fp;

    printf("\n\n\t\t\t\t-----One-Way Flight Booking-----\n\n");
    fp=fopen("Travel_management.txt","a");
    
    if(fp!=NULL)
    {
    	fprintf(fp,"\n\n-----One-Way Flight Booking-----\n\n");
    	displayCities();
    	printf("Choose departure city (1 to 5): ");
        scanf("%d",&departureChoice);
        getchar();
        printf("Choose destination city (1 to 5): ");
        scanf("%d",&destinationChoice);
        getchar();

       if(departureChoice<1 || departureChoice>cityCount || destinationChoice<1 || destinationChoice>cityCount)
	{
        while((departureChoice<1 || departureChoice>cityCount) && C_attempts<2)
                {
                    printf("Invalid departure city choice. Please try again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&departureChoice);
                    C_attempts++;
                    if((departureChoice<1 || departureChoice>cityCount) && C_attempts<2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
        C_attempts=0;
        while((destinationChoice<1 || destinationChoice>cityCount) && C_attempts<2)
                {
                    printf("Invalid departure city choice. Please try again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&destinationChoice);
                    C_attempts++;
                    if((destinationChoice<1 || destinationChoice>cityCount) && C_attempts<2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
    }
    if(departureChoice==destinationChoice)
	{
         while((departureChoice==destinationChoice) && C_attempts<2)
                {
                    printf("Departure and Destination city cannot be same. Please try again.");
                    printf("Enter the destination choice again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&destinationChoice);
                    C_attempts++;
                    if((departureChoice==destinationChoice) && C_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
    }
        	int D_attempts=0;
        	printf("Enter travel date: ");
            scanf("%s", date);
            getchar();
            
            if(strlen(date)!=10)
            {
            	printf("Date must be 10 characters long(DD-MM-YYYY)\n");
            	while(strlen(date)!=10 && D_attempts<2)
                {
                    printf("Invalid Date.Attempt using %d out of 2\n",D_attempts+1);
                    scanf("%s",date);
                    D_attempts++;
                    if(strlen(date)!=10 && D_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
			}
                strcpy(copydate,date);			
				char *day=strtok(date,"-");
				char *month=strtok(NULL,"-");
				char *year=strtok(NULL,"-");
				
				if(day==NULL || month==NULL || year==NULL)
				{
                while((day==NULL || month==NULL || year==NULL) && D_attempts<2)
                {
                    printf("Invalid format: Date must follow DD-MM-YYYY.Attempt using %d out of 2\n",D_attempts+1);
                    scanf("%s",date);
                    D_attempts++;
                    if(strlen(date)!=10 && D_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
            }
            printf("\nChoose the class:\n");
            printf("1. Economy\n");
            printf("2. Business\n");
            printf("3. First Class\n");
            printf("Enter your choice (1, 2, or 3): ");
            scanf("%d", &budgetChoice);
            getchar();
            
            system("cls");
            
            airlinechoice=displayAirlines(budgetChoice);
            
            printf("Enter number of adults: ");
            scanf("%d", &adults);
            getchar();
            printf("Enter number of children: ");
            scanf("%d", &children);
            getchar();
            
            system("cls");
            
            int cost_total=generateBillOneWay(budgetChoice, airlinechoice, adults, children);  //total bill  of  booking
            
            printf("\nSelected the Flight %s %d\n",Airlines[budgetChoice-1][airlinechoice],AirlinePrices[budgetChoice-1][airlinechoice]);
            int flag;
            printf("Do you want to confirm the booking.Press 1 for yes and 0 for No: ");
            scanf("%d",&flag);
            getchar();
            
            if(flag==1)
            {
            	printf("\nYour ONE-WAY flight has been successfully booked from %s to %s, departing on %s.\n",cities[departureChoice-1],cities[destinationChoice-1],copydate);
            	fprintf(fp,"\nTotal Cost for %d Adults and %d Children: PKR %d",adults,children,cost_total);
            	fprintf(fp,"\nUser %s one way flight has been successfully booked\nUser %s selected departure city %s and Destination city %s,Departing on %s.\n",name,name,cities[departureChoice-1],cities[destinationChoice-1],date);
                fprintf(fp,"\nSelected the Flight %s %d\n",Airlines[budgetChoice-1][airlinechoice],AirlinePrices[budgetChoice-1][airlinechoice]);
                fprintf(fp,"Flight was booked for %d Adults and %d Children\n",adults,children);
                
                fclose(fp);
                Destination_Hotel(destinationChoice);
			}
	        else
	        {
	        	fclose(fp);
		        return;
	        }
}
	else
	{
		printf("Could not open travel management file in One way trip");
	}
}
void bookRoundTripFlight(char name[],const char *cities[],const char *Airlines[3][3],int AirlinePrices[3][3])
{
    int destinationChoice,departureChoice, budgetChoice, airlineChoice, adults, children,D_attempts=0,C_attempts=0;
    char departureDate[20],copydepartureDate[20], returnDate[20], copyreturnDate[20];
    FILE *fp;
    fp=fopen("Travel_management.txt","a");
if(fp!=NULL)
{
    printf("\n\t\t\t\t--- Round-Trip Flight Booking ---\n");
    fprintf(fp,"\n------ Round-Trip Flight Booking -----\n");
    
    displayCities();
    printf("Choose departure city (1 to 5): ");
    scanf("%d", &departureChoice);
    getchar();
    printf("Choose destination city (1 to 5): ");
    scanf("%d", &destinationChoice);
    getchar();

    if(departureChoice<1 || departureChoice>cityCount || destinationChoice<1 || destinationChoice>cityCount)
	{
        while((departureChoice<1 || departureChoice>cityCount) && C_attempts<2)
                {
                    printf("Invalid departure city choice. Please try again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&departureChoice);
                    C_attempts++;
                    if((departureChoice<1 || departureChoice>cityCount) && C_attempts<2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
        C_attempts=0;
        while((destinationChoice<1 || destinationChoice>cityCount) && C_attempts<2)
                {
                    printf("Invalid departure city choice. Please try again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&destinationChoice);
                    C_attempts++;
                    if((destinationChoice<1 || destinationChoice>cityCount) && C_attempts<2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
    }
    else if(departureChoice==destinationChoice)
	{
         while((departureChoice==destinationChoice) && C_attempts<2)
                {
                    printf("Departure and Destination city cannot be same. Please try again.");
                    printf("\nEnter the destination choice again.Attempt using %d out of 2\n",C_attempts+1);
                    scanf("%d",&destinationChoice);
                    C_attempts++;
                    if((departureChoice==destinationChoice) && C_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
    }
    else
    {
    printf("Enter departure date: ");
    scanf("%s",departureDate);
    getchar();
    if(strlen(departureDate)!=10)
            {
            	printf("Date must be 10 characters long(DD-MM-YYYY)\n");
            	while(strlen(departureDate)!=10 && D_attempts<2)
                {
                    printf("Invalid Date.Attempt using %d out of 2\n",D_attempts+1);
                    scanf("%s",departureDate);
                    D_attempts++;
                    if(strlen(departureDate)!=10 && D_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
			}
			else
			{
				strcpy(copydepartureDate,departureDate);
				char *day=strtok(departureDate,"-");
				char *month=strtok(NULL,"-");
				char *year=strtok(NULL,"-");
				
				if(day==NULL || month==NULL || year==NULL)
				{
                    while((day==NULL || month==NULL || year==NULL) && D_attempts<2)
                {
                    printf("Invalid format: Date must follow DD-MM-YYYY.Attempt using %d out of 2\n",D_attempts+1);
                    scanf("%s",departureDate);
                    D_attempts++;
                    if((day==NULL || month==NULL || year==NULL) && D_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
                }
			}
    int RD_attempts=0;
    printf("Enter return date: ");
    scanf("%s",returnDate);
    getchar();
    if(strlen(returnDate)!=10)
            {
            	printf("Date must be 10 characters long(DD-MM-YYYY)\n");
            	while(strlen(returnDate)!=10 && RD_attempts<2)
                {
                    printf("Invalid Date.Attempt using %d out of 2\n",RD_attempts+1);
                    scanf("%s",returnDate);
                    RD_attempts++;
                    if(strlen(returnDate)!=10 && RD_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
			}
			else
			{
				strcpy(copyreturnDate,returnDate);
				char *day=strtok(returnDate,"-");
				char *month=strtok(NULL,"-");
				char *year=strtok(NULL,"-");
				
				if(day==NULL || month==NULL || year==NULL)
				{
                    while((day==NULL || month==NULL || year==NULL) && RD_attempts<2)
                {
                    printf("Invalid format: Date must follow DD-MM-YYYY.Attempt using %d out of 2\n",D_attempts+1);
                    scanf("%s",returnDate);
                    RD_attempts++;
                    if(strlen(returnDate)!=10 && RD_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
                }
			}
    
    printf("\nChoose the class for flight:\n");
    printf("1. Economy\n");
    printf("2. Business\n");
    printf("3. First Class\n");
    printf("Enter your choice (1, 2, or 3): ");
    scanf("%d",&budgetChoice);
    getchar();
    
    system("cls");

    int airlinechoice=displayAirlines(budgetChoice);

    printf("Enter number of adults: ");
    scanf("%d",&adults);
    getchar();
    printf("Enter number of children: ");
    scanf("%d",&children);
    getchar();
    
    system("cls");

    int no_discount=generateBillRoundTrip(budgetChoice, airlinechoice, adults,children);
    
    printf("\nSelected the Flight %s %d\n",Airlines[budgetChoice-1][airlinechoice],AirlinePrices[budgetChoice-1][airlinechoice]);
    int flag;
    printf("Do you want to confirm the booking.Press 1 for yes and 0 for No: ");
    scanf("%d",&flag);
    getchar();
    
    if(flag==1)
    {
    	printf("\nYour round-trip flight has been successfully booked from %s to %s, departure on %s and returning on %s.\n",cities[departureChoice - 1], cities[destinationChoice - 1], copydepartureDate, copyreturnDate);
    	
    	fprintf(fp,"Total Cost before Discount for %d Adults and %d Children: PKR %d\n",adults,children,no_discount);
        fprintf(fp,"Total Cost after 10%% Discount: PKR %d\n",discountedCost);
    	fprintf(fp,"\nUser %s round-trip flight has been successfully booked\nUser %s selected departure city %s and Destination city %s with Departure date %s and return date %s\n",name,name,cities[departureChoice-1],cities[destinationChoice-1],departureDate,returnDate);
        fprintf(fp,"Selected the Flight %s %d\n",Airlines[budgetChoice-1][airlinechoice],AirlinePrices[budgetChoice-1][airlinechoice]);
        fprintf(fp,"Flight was booked for %d Adults and %d Children\n",adults,children);
        fclose(fp);
        
    	Destination_Hotel(destinationChoice);
	}
	else
	{
		fclose(fp);
		return;
	}
}
}
else
{
	printf("Error in opening the file in round trip flight");
}
}
int generateBillOneWay(int budgetChoice,int airlinechoice,int adults, int children)
{
    int costPerAdult,costPerChild;
    int totalCost;

    if(budgetChoice==1)
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[0][0];
            costPerChild=AirlinePrices[0][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[0][1];
            costPerChild=AirlinePrices[0][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[0][2];
            costPerChild=AirlinePrices[0][2] / 2;
		}
		else
		{
			printf("Invalid airline choice\n");
		}
    }
	else if(budgetChoice==2)
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[1][0];
            costPerChild=AirlinePrices[1][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[1][1];
            costPerChild=AirlinePrices[1][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[1][2];
            costPerChild=AirlinePrices[1][2] / 2;
		}
		else
		{
			printf("Invalid airline choice\n");
		}
    }
	else
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[2][0];
            costPerChild=AirlinePrices[2][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[2][1];
            costPerChild=AirlinePrices[2][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[2][2];
            costPerChild=AirlinePrices[2][2] / 2;
		}
		else
		{
			printf("Invalid airline choice\n");
		}
    }

    printf("\n--- Pricing Information ---\n");
    printf("Adult Ticket Price: PKR %d\n",costPerAdult);
    printf("Child Ticket Price: PKR %d\n",costPerChild);

    totalCost=(adults * costPerAdult)+(children * costPerChild);
    printf("Total Cost for %d Adults and %d Children: PKR %d\n", adults, children, totalCost);
    
    return totalCost;
}
int generateBillRoundTrip(int budgetChoice, int airlinechoice, int adults, int children)
{
    int costPerAdult,costPerChild;
    int totalCost;

    if (budgetChoice==1)
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[0][0];
            costPerChild=AirlinePrices[0][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[0][1];
            costPerChild=AirlinePrices[0][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[0][2];
            costPerChild=AirlinePrices[0][2] / 2;
		}
		else
		{
			printf("Invalid\n");
		}
    }
	else if(budgetChoice==2)
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[1][0];
            costPerChild=AirlinePrices[1][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[1][1];
            costPerChild=AirlinePrices[1][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[1][2];
            costPerChild=AirlinePrices[1][2] / 2;
		}
		else
		{
			printf("Invalid\n");
		}
    }
	else
	{
		if(airlinechoice==0)
		{
			costPerAdult=AirlinePrices[2][0];
            costPerChild=AirlinePrices[2][0] / 2;
		}
		else if(airlinechoice==1)
		{
			costPerAdult=AirlinePrices[2][1];
            costPerChild=AirlinePrices[2][1] / 2;
		}
		else if(airlinechoice==2)
		{
			costPerAdult=AirlinePrices[2][2];
            costPerChild=AirlinePrices[2][2] / 2;
		}
		else
		{
			printf("Invalid\n");
		}
    }
    printf("\n--- Pricing Information ---\n");
    printf("Adult Ticket Price: PKR %d\n",costPerAdult);
    printf("Child Ticket Price: PKR %d\n",costPerChild);

    totalCost= (adults*costPerAdult)+(children*costPerChild);
    discountedCost=totalCost-(totalCost*0.1);

    printf("Total Cost before Discount for %d Adults and %d Children: PKR %d\n",adults,children,totalCost);
    printf("Total Cost after 10%% Discount: PKR %d\n",discountedCost);
    
    return totalCost;
}

bool isValidEmail(const char *email)
{   
    const char *atPtr=strchr(email,'@');
    if(atPtr==NULL || atPtr==email)
	{
        return false; 
    }
    const char *gmailptr=strstr(email,"gmail");
    
    if(gmailptr==NULL)
    {
    	return false;
	}
	
    const char *dotPtr=strchr(atPtr,'.');
    
    if (dotPtr==NULL || dotPtr==atPtr+1 || dotPtr!=gmailptr+5)
	{
        return false;
    }
    return true;
}
int registeredmanager(char *maname)
{
	char Mnames[2][10]={"Manager1","Admin2"};
	
	for(int i=0;i<2;i++)
	{
		if((strcmp(maname,Mnames[i])==0))
		{
			return 1;
		}
	}
	return 0;
	
}
int IsValiduserName(char *username)
{
    int len=strlen(username);

    if(!isalpha(username[0]))    // It will check that first character must be a letter (either lowercase or uppercase)
	{
        printf("Username must start with an alphabetic character.\n");
        return 0;
    }

    for(int j=1;j<len;j++)     // It checks that the remaining characters are alphanumeric or underscores
	{
        if(!isalnum(username[j]) && username[j]!='_')
		{
            printf("Username can only contain letters, numbers, and underscores.\n");
            return 0;
        }
    }

    return 1;
}
void View()
{
	char booking_read[256];
	FILE *fp,*user;
	fp=fopen("Manager.txt","a");
	if(fp==NULL)
	{
		printf("Error in opening the manager file\n");
		return;
	}
	user=fopen("Travel_management.txt","r");
	if(user==NULL)
	{
		printf("Error in viewing the user file\n");
		fclose(fp);
		return;
	}
	
	fprintf(fp,"Bookings made are:\n");
	while(fgets(booking_read,256,user)!=NULL)
	{
		fputs(booking_read,fp);
		fputs("\n",fp);
	}
	printf("Bookings successfully copied into manager file\n");
	fclose(user);
	fclose(fp);
}
void Update(struct Manager M1)
{
    int choice;
    printf("Update Options:\n1.Update Hotels\n2.Update airline\n");
    scanf("%d",&choice);
    
    FILE  *upfile;
    upfile=fopen("Manager.txt","a");
    if(upfile==NULL)
    {
    	printf("Error in opening the file in update");
	}
    switch(choice)
	{
        case 1:
        	printf("\nManager %s selected to update hotel description\n",M1.name);
            fprintf(upfile,"\nManager %s selected to update hotel description\n",M1.name);
            updateHotels();
            break;
        case 2:
        	printf("\nManager %s selected to update airline price\n",M1.name);
            fprintf(upfile,"\nManager %s selected to update airline price\n",M1.name);
            update_airlines(AirlinePrices,Airlines);
            break;
        default:
            printf("Invalid choice in update.\n");
    }
    fclose(upfile);
}
void updateHotels()
{
    int i,j;
    FILE *hotelup;
    hotelup=fopen("Manager.txt","a");
    if(hotelup==NULL)
	{
        printf("Error in opening the manager file in update hotels\n");
        return;
    }
    printf("\nHotels Before Update:\n");
    for(i=0;i<max_hotels;i++)
	{
        printf("%s:\n",cities[i]);
        for(j=0;j<lim;j++)
		{
            if(HotelsDescription[i][j]!=NULL)
			{
                printf("%d. %s\n",j+1,HotelsDescription[i][j]);
            }
			else
			{
                printf("%d.(No Description)\n",j+1);
            }
        }
        printf("\n");
    }

    int opt;
    displayCities();
    printf("In which city do you want to change the hotel description? (1-%d):\n",max_hotels);
    scanf("%d",&opt);
    getchar(); // Clear the newline character from input buffer

    if(opt<1 || opt>max_hotels)
	{
        printf("Invalid city choice.\n");
        fclose(hotelup);
        return;
    }

    // Update descriptions for the selected city
    for(j=0;j<lim;j++)
	{
        printf("Changing the hotel description %d of %s:\n",j+1,cities[opt-1]);
        // Allocate memory for the new description
        HotelsDescription[opt-1][j]=(char *)malloc(200 * sizeof(char));
        if(HotelsDescription[opt-1][j]==NULL)
		{
            printf("Memory allocation failed for hotel description.\n");
            fclose(hotelup);
            return;
        }
        fgets(HotelsDescription[opt-1][j],200,stdin);// Read the new description
        //this will remove previous newline
        HotelsDescription[opt-1][j][strcspn(HotelsDescription[opt-1][j],"\n")]='\0';
    }
    // Print updated hotels
    printf("\nAfter Update:\n");
    fprintf(hotelup, "\n\nAfter Update\n\n");
    for(i=0;i<max_hotels;i++)
	{
        printf("%s:\n",cities[i]);
        fprintf(hotelup,"%s:\n",cities[i]);
        for(j=0;j<lim;j++)
		{
            if(HotelsDescription[i][j]!=NULL)
			{
                printf("%d. %s\n",j+1,HotelsDescription[i][j]);
                fprintf(hotelup,"%d. %s\n",j+1,HotelsDescription[i][j]);
            }
			else
			{
                printf("%d.(No Description)\n",j+1);
                fprintf(hotelup,"%d.(No Description)\n",j+1);
            }
        }
        printf("\n");
        fprintf(hotelup,"\n");
    }
    printf("Description changed successfully\n");
    fprintf(hotelup,"Description changed successfully\n");
    fclose(hotelup);
}
//updating airline prices
void update_airlines(int AirlinePrices[3][3],const char *Airlines[3][3])
{
	FILE *airline_up;
	airline_up=fopen("Manager.txt","a");
	if(airline_up==NULL)
	{
		printf("Error in opening the file in updating airline prices\n");
	}
	printf("Before update Airline prices\n");
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			printf("%s\n",Airlines[i][j]);
			printf("%d.)%d\n",j+1,AirlinePrices[i][j]);
		}
		printf("\n");
	}
	char select_Airline[50];
	int found=0,row,col,price;
	fflush(stdin);
	printf("Which airline price you want to update?(Write whole name):\n ");
	scanf("%[^\n]",select_Airline);
	//Searching the airline
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			if(strcmp(select_Airline,Airlines[i][j])==0)
			{
				found=1;
				row=i;
				col=j;
				break;
			}
		}
	}
	if(found==1)
	{
		printf("Current price for %s: %d\n",Airlines[row][col],AirlinePrices[row][col]);
        printf("Enter the new price: ");
        scanf("%d",&price);
        
        AirlinePrices[row][col]=price;
        printf("After update Airline prices are:\n");
        fprintf(airline_up,"\nAfter update Airline prices are:\n");
        //updated airline prices
        for(int i=0;i<3;i++)
        {
        	for(int j=0;j<3;j++)
        	{
        		printf("%s\n",Airlines[i][j]);
			    printf("%d.)%d\n",j+1,AirlinePrices[i][j]);
			    fprintf(airline_up,"%s\n",Airlines[i][j]);
			    fprintf(airline_up,"%d.)%d\n",j+1,AirlinePrices[i][j]);
			}
			printf("\n");
			fprintf(airline_up,"\n");
		}
	}
	else
	{
		printf("Cannot find the airline for updating price\n");
		return;
	}
	printf("Price updated successfully/n");
	fprintf(airline_up,"Price updated successfully/n");
	
	fclose(airline_up);
}
void Remove(struct Manager M1)
{
	int r_choice;
    printf("Remove Options:\n1.Remove Hotels\n2.Remove airline\n");
    scanf("%d",&r_choice);
    FILE *filep2;
    filep2=fopen("Manager.txt","a");
    if(filep2==NULL)
    {
    	printf("Error in opening the file for removing\n");
	}
    switch(r_choice)
	{
        case 1:
        	printf("\nManager %s selected to remove hotel\n",M1.name);
            fprintf(filep2,"\nManager %s selected to remove hotel\n",M1.name);
            remove_hotels(cities);
            break;
        case 2:
        	printf("\nManager %s selected to remove airline\n",M1.name);
            fprintf(filep2,"\nManager %s selected to remove airline\n",M1.name);
            remove_airlines(AirlinePrices,Airlines);
            break;
        default:
            printf("Invalid choice in remove.\n");
    }
    fclose(filep2);
}
//Removing Hotels
void remove_hotels(const char *cities[5])
{
	FILE *rem;
	rem=fopen("Manager.txt","a");
	if(rem==NULL)
	{
		printf("Error in opening the manager file for removing");
	}
	printf("Hotels Before removing\n");
	for(int i=0;i<max_hotels;i++)
	{
        printf("%s:\n",cities[i]);
        for(int j=0;j<lim;j++)
		{
            if(HotelsDescription[i][j]!=NULL)
			{
                printf("%d. %s\n",j+1,HotelsDescription[i][j]);
            }
        }
        printf("\n");
    }
    char select_city[50];
    int rh_choice,found=0,city_index;
    getchar();
    printf("Which city hotel you want to remove\n");
    scanf("%[^\n]",select_city);
    getchar();
    for(int i=0;i<max_hotels;i++)
	{
		if(strcmp(select_city,cities[i])==0)
		{
			found=1;
			city_index=i;
			break;
		}
	}
	if(found==0)
	{
		printf("City not found\n;");
		fclose(rem);
		return;
	}
	printf("Hotels in the city %s are:\n",cities[city_index]);
	for(int i=0;i<lim;i++)
	{
		printf("%d.)%s\n",i+1,HotelsDescription[city_index][i]);
	}
    printf("Select the hotel you want to remove by number:\n");
    scanf("%d",&rh_choice);
    
    if(rh_choice<1 && rh_choice>3)
    {
    	printf("Invalid choice to remove hotel");
    	fclose(rem);
    	return;
	}
	else
	{
		HotelsDescription[city_index][rh_choice-1]=NULL;
	}
	printf("After removing:\n");
	fprintf(rem,"After removing:\n");
	for(int i=0;i<max_hotels;i++)
	{
		printf("%s:\n",cities[i]);
		for(int j=0;j<lim;j++)
		{
			if(HotelsDescription[i][j] !=NULL)
			{
				printf("%d.)%s\n",j+1,HotelsDescription[i][j]);
				fprintf(rem,"%d.)%s\n",j+1,HotelsDescription[i][j]);
			}
		}
		printf("\n");
		fprintf(rem,"\n");
	}
	printf("Hotel removed successfully\n");
	fprintf(rem,"Hotel removed successfully\n");
    fclose(rem);
}
//function for removing airrlines
void remove_airlines(int AirlinePrices[3][3],const char *Airlines[3][3])
{
	FILE *rem_air;
	rem_air=fopen("Manager.txt","a");
	if(rem_air==NULL)
	{
		printf("Error in opening the file for removing airlines\n");
		return;
	}
	printf("Before removing Airline prices\n");
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			printf("%s\n",Airlines[i][j]);
			printf("%d.)%d\n",j+1,AirlinePrices[i][j]);
		}
		printf("\n");
	}
	char rem_Airline[50];
	int found=0,r_row,r_col;
	getchar();
	printf("Which airline price you want to remove?(Write the whole name):\n ");
	scanf("%[^\n]",rem_Airline);
	getchar();

	//searching the airline to remoove
	for(int i=0;i<3;i++)
	{
		for(int j=0;j<3;j++)
		{
			if(strcmp(rem_Airline,Airlines[i][j])==0)
			{
				found=1;
				r_row=i;
				r_col=j;
				break;
			}
		}
	}
	if(found==0)
	{
		printf("could not fine the airline you want to remove\n");
		fclose(rem_air);
		return;
	}
	Airlines[r_row][r_col]="NO Airline";    //To identify which one to remove att the time of printing after removing it.
	AirlinePrices[r_row][r_col]=0;
	
	printf("After removing the airline\n");
	fprintf(rem_air,"After removing the airline\n");
	
	for(int i=0;i<lim;i++)
	{
		printf("%s:\n",Airlines[i][0]);
		fprintf(rem_air,"%s:\n",Airlines[i][0]);
		for(int j=0;j<lim;j++)
		{
			if(strcmp(Airlines[i][j],"NO Airline") && AirlinePrices[i][j]!=0)
			{
				printf("%d.)%s -Price %d\n",j+1,Airlines[i][j],AirlinePrices[i][j]);
				fprintf(rem_air,"%d.)%s -Price %d\n",j+1,Airlines[i][j],AirlinePrices[i][j]);
			}
		}
		printf("\n");
		fprintf(rem_air,"\n");
	}
	printf("Airline %s removed successfully\n",rem_Airline);
	fprintf(rem_air,"Airline %s removed successfully\n",rem_Airline);
	fclose(rem_air);
}
//DASHBOARD FOR MANAGER 
void dashboard(struct Manager M1)
{
	FILE *filep1;
	filep1=fopen("Manager.txt","a");
	
	if(filep1==NULL)
	{
		printf("Error in opening the file in dashboard");
		return;
	}
	int func;
    printf("\nWhich functionality you want to use(Select the number)?\n1.View\n2.Update\n3.Remove\n4.Exit\n");
    scanf("%d",&func);
    getchar();

        switch(func)
        {
            case 1:
            	printf("\nManager %s selected to view bookings\n",M1.name);
            	fprintf(filep1,"\nManager %s selected to view bookings\n",M1.name);
            	fclose(filep1);
                View();
                break;
            case 2:
            	printf("\nManager %s selected to Update\n",M1.name);
            	fprintf(filep1,"Manager %s selected to Update\n",M1.name);
            	fclose(filep1);
                Update(M1);
                break;
            case 3:
            	printf("\nManager %s selected to remove\n",M1.name);
            	fprintf(filep1,"\nManager %s selected to remove\n",M1.name);
            	fclose(filep1);
                Remove(M1);
                break;
            case 4:
            	printf("Exiting\n");
            	fclose(filep1);
            	break;
            default:
            	printf("Invalid choice.\n");
            	fclose(filep1);
                break;
		}

}
void manager_signup(struct Manager M1)     //manager login system
{
    char re_pass[50];
    int M_attempts=0,UM_attempts=0;
    
    FILE *filepointer=fopen("Manager.txt","a");
    if(filepointer!=NULL)
    {
                printf("Enter your Username:\n");
                scanf("%s",M1.name);
                getchar();
                while((registeredmanager(M1.name)==0) && UM_attempts<2)
                {
                    printf("Username do not match.Please re-enter your username.Attempt using %d out of 2\n",UM_attempts+1);
                    scanf("%s",M1.name);
                    getchar();
                    UM_attempts++;
                    if((registeredmanager(M1.name)==0) && UM_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }
                
                printf("Enter your password:\n");
                fgets(M1.pass,sizeof(M1.pass),stdin);
                M1.pass[strcspn(M1.pass,"\n")]=0;
            
                strcpy(re_pass,"manager12");
                
                while(strcmp(M1.pass,re_pass)!=0 && M_attempts<2)
                {
                    printf("Passwords do not match.Please re-enter the password.Attempt using %d out of 2\n",M_attempts+1);
                    scanf("%s",M1.pass);
                    M_attempts++;
                    if(strcmp(M1.pass,re_pass)!=0 && M_attempts==2)
                    {
                	    printf("\nMaximum Attempts reached.Exiting...");
                	    return;
                    }
                }

                printf("Hi %s!, you're logged in.\n", M1.name);
                fprintf(filepointer,"Hi %s!, you're logged in.\n",M1.name);
                fclose(filepointer);
                dashboard(M1);
        
    }
	else
	{
	    printf("ERROR: could not open file in manager signup");
	    return;
	}
}
//user login system
void CUSTOMER_DATA()
{
	FILE *fp;
	fp=fopen("Travel_management.txt","a");
	if(fp!=NULL)
	{
        int acc, opt;
       char pass[50], re_pass[50], email[50];
        int e_attempts=0,Uattempts=0;
        
        fprintf(fp,"\n\t\tLogin/Signup\n");
        printf("Do you have an account?\n 1. No\n 2. Yes\n");
        scanf("%d",&acc);
        getchar();
        switch(acc)
	    {
            case 1:
            	
                printf("Sign up to create an account\n");
                printf("Enter your email:\n");
                scanf("%s",email);
                do{
                    if(!isValidEmail(email) && e_attempts<2)
					{
                        printf("Invalid email format. Please enter a valid email (Attempt %d out of 2):\n",e_attempts+1); 
                        scanf("%s",email);
                        e_attempts++;
                        if((!isValidEmail(email)) && e_attempts==2)
                        {
                	        printf("\nMaximum Attempts reached.Exiting...");
                	        return;
				        }
                    }
                }while((!isValidEmail(email))&& e_attempts<2);
                
                printf("Enter your username:\n");
                scanf("%s",name);
                getchar();
                  while(!IsValiduserName(name) && Uattempts < 2)
				{
                  printf("Please re-enter the username. Attempt using %d out of 2\n", Uattempts+1); 
                  scanf("%s", name);
                  getchar(); // Clear the newline
                Uattempts++;
                    if(!IsValiduserName(name) && Uattempts == 2)
					{
                        printf("\nMaximum Attempts reached. Exiting...");
                        fclose(fp);
                        return;
                    }
                }
				
                printf("ENTER PASSWORD:\n");
                scanf("%s",pass);
                getchar();
                printf("Confirm your password:\n");
                scanf("%s",re_pass);
                getchar();
                    
                int attempts=0;
                do{
                    if((strcmp(pass,re_pass)!=0) && attempts<2)
					{
                        printf("Passwords do not match.Please re-enter the password.Attempt using %d out of 2\n",attempts+1); 
                        scanf("%s",re_pass);
                        attempts++;
                        if((strcmp(re_pass,pass)!=0) && attempts==2)
                        {
                	        printf("\nMaximum Attempts reached.Exiting...");
                	        return;
				        }
                    }
                }while((strcmp(pass,re_pass)!=0)&& attempts<2);

                fprintf(fp,"ACCOUNT CREATED SUCCESSFULLY! \nWELCOME ON BOARD %s!\n",name);
                fprintf(fp,"USER:\nEmail:%s\nUsername:%s\n",email,name);  
                printf("ACCOUNT CREATED SUCCESSFULLY! \nWELCOME ON BOARD %s!\n",name);
                fclose(fp);
                system("cls");
                Booking(name,Airlines,AirlinePrices);
                break;
            case 2:
                printf("Enter your Username:\n");
                scanf("%s",name);
                getchar();
                while(!IsValiduserName(name) && Uattempts < 2)
				{
                  printf("Please re-enter the username. Attempt using %d out of 2\n", Uattempts+1); 
                  scanf("%s", name);
                  getchar(); // Clear the newline
                Uattempts++;
                    if(!IsValiduserName(name) && Uattempts == 2)
					{
                        printf("\nMaximum Attempts reached. Exiting...");
                        fclose(fp);
                        return;
                    }
                }
				
                printf("Enter your password:\n");
                scanf("%s", pass);
                getchar();
                printf("USER:\nHi %s!,you're logged in.\n",name);
                fprintf(fp,"USER:\nHi %s!,you're logged in.\n",name);
                fclose(fp);
                system("cls");
                Booking(name,Airlines,AirlinePrices);
                break;
            default:
                printf("Invalid Input\n");
                return;
                break;
        }
    }  
	else
	{
	    printf("ERROR: could not open file in Customer Data\n");
	    return;
	}
}
void Booking(char name[],const char *Airlines[3][3],int AirlinePrices[3][3])
{
	FILE *fp;
	fp=fopen("Travel_management.txt","a");
	if(fp!=NULL)
	{
		int choice,b_attempts=0;
		
        printf("\n------------------------------Welcome to the Domestic Flight Booking System--------------------------------\n");
        fprintf(fp,"\n\t\tBOOKING Flights\t\t\n");
//        displayCities();
        do{
			printf("\nPlease choose the type of flight:\n1. One-Way Flight\n2. Round-Trip Flight\n");
            scanf("%d",&choice);
            b_attempts++;

            switch(choice)
			{
                case 1:
                    printf("\nUser %s selected One-Way Flight\n",name);
                    fprintf(fp,"\nUser %s selected One-Way Flight",name);
               	    fclose(fp);
               	    system("cls");
                    bookOneWayFlight(name,cities,Airlines,AirlinePrices);
                    break;
                case 2:
                    printf("User %s selected Round-Trip Flight",name);
                    fprintf(fp,"\nUser %s selected Round-Trip Flight\n",name);
                    fclose(fp);
                    system("cls");
                    bookRoundTripFlight(name,cities,Airlines,AirlinePrices);
                    break;
                default:
                    printf("Invalid choice. Please choose from 1 or 2.\n");
                    fclose(fp);
                    Booking(name,Airlines,AirlinePrices);
                    break;
            }
            if(b_attempts==2 && (choice<1 || choice>2))
			{ 
                printf("Maximum attempts reached. Exiting...\n"); // This will make it exit after 2 invalid attempts
                fclose(fp);
                return; //to terminate the program
            }
	    }while(b_attempts<2 && (choice<1 || choice>2));
	}
	else
	{
		printf("ERROR: could not open travel management file in Booking and ticketing");
		return;
	}  
}
void  Destination_Hotel(int destinationChoice)
{
		FILE *fp=fopen("Travel_management.txt","a");
		if(fp!=NULL)
		{
          int HotelChoice,NoOfNights;
          int Prices[5][3]={{5000,10000,16000},
                             {6000,9000,14000},
	                         {7000,11000,18000},
	                         {4000,8000,13000},
	                          {4500,9400,14500}
	                        };
   
        if(destinationChoice>=1 && destinationChoice<=5)
		{
			int ans;
			printf("Would you like to book the hotel also through our app?:(1 for yes or 0 for no)");
			scanf("%d",&ans);
			system("cls");
			if(ans==1)
			{
				printf("\nThe hotels available in %s are:\n",cities[destinationChoice-1]);
				for (int i=0;i<3;i++)
				{
					printf("%d.) %s\n",i+1,HotelsDescription[destinationChoice-1][i]);
				}
			}
			else
			{
				int h_attempts=0;
				while((ans!=0 && ans!=1) && h_attempts<2)
				{
					printf("please choose from 1 and 0 (1 for yes and 0 for no).you are currently using the attempt %d out of 2:",h_attempts+1);
					scanf("%d",&ans);
					h_attempts++;
					if(h_attempts>=2 &&(ans!=0 && ans!=1))
					{
						printf("MAximum attempts reached.Exiting...\n");
						return;
					}
			    }
				if(ans==0)
				{
					printf("No problem! Have a great trip:)");
				    return;
				}
				else
				{
					printf("\nThe hotels available in %s are:\n",cities[destinationChoice-1]);
				    for (int i=0;i<3;i++)
				    {
				    	printf("%d.) %s\n",i+1,HotelsDescription[destinationChoice-1][i]);
				    }
				}
			}
        }
		else
		{
            printf("Incorrect destination choice\n");
            return;
        }
   printf("\nselect the hotel you want to stay in by  number: ");
   scanf("%d",&HotelChoice);
   
   if(HotelChoice<1||HotelChoice>3)
   {
        printf("\nthe user entered an invalid input");
        return ;
   }
   printf("\nenter the number of nights you want to stay: ");
   scanf("%d",&NoOfNights);
   
   if(NoOfNights<=0)
   {
        printf("Invalid number of nights. Please try again.\n");
        return;
   }
   int TotalCost=Prices[destinationChoice-1][HotelChoice-1]*NoOfNights;
   printf("\nThe total cost for your stay will be:%d\n",TotalCost);
   printf("Do you want to comfirm the booking?\nYes or No:\n");
   char  choice[4];
   scanf("%s",&choice);
   if(strcmp(choice,"Yes")==0 || strcmp(choice,"yes")==0)
   {
   	    printf("\nYour booking is now confirmed!\nyou have selected %s in %s for %d nights to stay",HotelsDescription[destinationChoice-1][HotelChoice-1],cities[destinationChoice-1],NoOfNights);
   	    printf("\nHave a nice Trip :)");
		fprintf(fp,"\nUser %s booking is now confirmed!\nUser %s selected %s in %s for %d nights to stay",name,name,HotelsDescription[destinationChoice-1][HotelChoice-1],cities[destinationChoice-1],NoOfNights);
   }
   else
   {
   	return ;
   }
   fclose(fp);
 }
    else
       {
			printf("ERROR: could not open Destination and hotel selectment file");
		    return;
		}
}
void TRAVEL_MANAGEMENT_FILE()
{
	FILE *fp=fopen("Travel_management.txt","a");
	if(fp!=NULL)
	{
		time_t now=time(NULL);
		fprintf(fp,"\n\nOpened: %s\n",ctime(&now));
		fclose(fp);
	}
	else
	{
		printf("ERROR: could not open Travel management file");
		return;
	}	
}
int main()
{
	int option;
	struct Manager M1;
	
	printf("\t\t-------Login/Signup--------\n");

    do{
    	printf("Are you a: \n 1.USER\n 2.MANAGER\n(choose 1 or 2)\n");
        scanf("%d", &option);
    	switch(option)
        {
    	    case 1:
    		    CUSTOMER_DATA();
    		    break;
    	    case 2:
    		    manager_signup(M1);
    		    break;
    	    default:
    		    printf("Invalid option.\nPlease input either 1 or 2\n");
	    }
	}while(option<1 || option>2);
TRAVEL_MANAGEMENT_FILE();
	
	return 0;
}