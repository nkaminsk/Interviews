#Nicholas Kaminsky
#Programming Interview - MediTechSafe/ResilAnt
#Security Analyst/Software Engineer
#Web Scraping Project
#1/6/2022

"""
pip install tk
pip install beautifulsoup4
pip install request
"""
#tkinter GUI intefcace library
import tkinter as tk
from tkinter import ttk
from tkinter import scrolledtext

#HTML requests library
import requests 

#HTML parsing library
from bs4 import BeautifulSoup 

#Database library
import sqlite3 

print("Loading... Please Wait.") #This should disappear when saved as .pyw

#create/open database connection
con = sqlite3.connect('vulnerability_book.db')

cur = con.cursor()

#check if table exists
cur.execute(''' SELECT count(name) FROM sqlite_master WHERE type='table' AND name='vulnerabilities' ''')
if cur.fetchone()[0]==1 : 
	#print('Table exists.')
    pass
else :
    #create the table if not existing
    cur.execute('''CREATE TABLE vulnerabilities
              (id_num text,
              cve_id text,
              cwe_id text,
              num_exploits text,
              vuln_type text,
              pub_date text,
              up_date text,
              score text,
              access_level text,
              access text,
              complexity text,
              authenticaiton text,
              conf text,
              integ text,
              avail text,
              description text
              )''')
    

# Save (commit) the changes and close the table intitalization
con.commit()
con.close()

#This is the link you provided me
ogURL = "https://www.cvedetails.com/vulnerability-list/year-2022/month-1/January.html"
pageinfo = requests.get(ogURL)

#pulls the HTML GET request into an HTML parsing object
soupContent = BeautifulSoup(pageinfo.content, "html.parser")

#find the total page count on the page by sorting through the HTML
soupPages = soupContent.find_all('div',class_="paging")
totalPages = soupPages[1].find_all('a')
totalPages = len(totalPages)

page = 1
firstURL = "https://www.cvedetails.com/vulnerability-list.php?vendor_id=0&product_id=0&version_id=0&page="
lastURL = "&hasexp=0&opdos=0&opec=0&opov=0&opcsrf=0&opgpriv=0&opsqli=0&opxss=0&opdirt=0&opmemc=0&ophttprs=0&opbyp=0&opfileinc=0&opginf=0&cvssscoremin=0&cvssscoremax=0&year=2022&month=1&cweid=0&order=1&trc=288&sha=0a3fee031e44b158a5b57162139c11a9a9c7f07c"

#Iterate through all pages to pull all vulnerabilities
#I like while loops
while page<totalPages:
    #a new page gets called every iteration
    mainURL = firstURL + str(page) + lastURL
    page = page + 1
    pageinfo = requests.get(mainURL)

    #grabs all the HTML from the address
    soupContent = BeautifulSoup(pageinfo.content, "html.parser")

    #sorts through the HTML to grab just the vulberability table
    soupVulns = soupContent.find(id="vulnslisttable")
    #sorts through the table to find the vulnerabilities data
    vulnerabilities = soupVulns.find_all("tr", class_="srrowns")
    #sorts through the table to grab the descriptions data 
    vulndescripts = soupVulns.find_all("td", class_="cvesummarylong")

    #must create new connection to database for every page (so the sqlite3 library says)
    conn = sqlite3.connect('vulnerability_book.db')
    cur = conn.cursor()

    i=0 #counter for descriptions to follow the matching vulnerabilities

    for vuln in vulnerabilities:
        #puts a single vulnerabilities data into a list
        vulntds = vuln.find_all("td")

        #adds the corresponding data to the database
        cur.execute("INSERT INTO vulnerabilities VALUES (:id_num, :cve_id, :cwe_id, :num_exploits, :vuln_type, :pub_date, :up_date, :score, :access_level, :access, :complexity, :authentication, :conf, :integ, :avail, :description)",
                {
                    'id_num': vulntds[0].text.strip(),
                    'cve_id': vulntds[1].text.strip(),
                    'cwe_id': vulntds[2].text.strip(),
                    'num_exploits': vulntds[3].text.strip(),
                    'vuln_type': vulntds[4].text.strip(),
                    'pub_date': vulntds[5].text.strip(),
                    'up_date': vulntds[6].text.strip(),
                    'score': vulntds[7].text.strip(),
                    'access_level': vulntds[8].text.strip(),
                    'access': vulntds[9].text.strip(),
                    'complexity': vulntds[10].text.strip(),
                    'authentication': vulntds[11].text.strip(),
                    'conf': vulntds[12].text.strip(),
                    'integ': vulntds[13].text.strip(),
                    'avail': vulntds[14].text.strip(),
                    'description': vulndescripts[i].text.strip()
                }
                )

        #follow the count for descriptions
        i = i + 1

        #save changes and close
        conn.commit()

    conn.close()


#grab the fully loaded parse to output into GUI
con = sqlite3.connect('vulnerability_book.db')
cur = con.cursor()
cur.execute('SELECT *, oid FROM vulnerabilities')
dataEntries = cur.fetchall()
con.close()

#formats the data into a really long string
print_data = ''
for record in dataEntries:
        print_data += str(record) + '\n'

#This is the root window of the tkinter GUI
window = tk.Tk()

#add aestetics here
window.title("ResilAnt Coding Experiment")

#initializes the labels
urlLabel = tk.Label(window, text="URL :: \n https://www.cvedetails.com/vulnerability-list/year-2022/month-1/January.html")
urlLabel2 = tk.Label(window, text="Ammended URL :: \n https://www.cvedetails.com/vulnerability-list.php?vendor_id=0&product_id=0&version_id=0&page=XXX \n &hasexp=0&opdos=0&opec=0&opov=0&opcsrf=0&opgpriv=0&opsqli=0&opxss=0&opdirt=0&opmemc=0&ophttprs=0&opbyp=0&opfileinc=0&opginf=0&cvssscoremin=0&cvssscoremax=0&year=2022&month=1&cweid=0&order=1 \n &trc=288&sha=0a3fee031e44b158a5b57162139c11a9a9c7f07c")

#places the label into the layout
urlLabel.grid(padx=10, pady=10,row=0, column=0)
urlLabel2.grid(padx=10, pady=10,row=5,column=0)

#scrollble text area
text_area = scrolledtext.ScrolledText(window, height = 20, width = 100)
text_area.insert(tk.INSERT, print_data)
text_area.grid(row=20, column=0)
text_area.configure(state ='disabled')


window.mainloop() #tkinter window starts here

