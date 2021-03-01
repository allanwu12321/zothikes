#scraper for CS 125
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from pymongo import MongoClient

city = "irvine"
url = "https://www.alltrails.com/us/california/" + city

chromedriver = r'C:/Users/rione\Downloads/chromedriver_win32/chromedriver.exe'
driver = webdriver.Chrome(executable_path = chromedriver)
driver.get(url)
driver.find_element_by_xpath("//button[text()='Show more trails']").click() # click on button to show more trails
driver.find_element_by_xpath("//button[text()='Show more trails']").click()
elements = WebDriverWait(driver, 20).until(EC.visibility_of_all_elements_located((By.CLASS_NAME, "styles-module__name___3T41O")))
names = driver.find_elements_by_class_name("styles-module__name___3T41O")
difficulty = driver.find_elements_by_class_name("styles-module__diff___22Qtv")
rating = driver.find_elements_by_class_name("MuiRating-root")
length = driver.find_elements_by_xpath("//span[text()='Length']")
time = driver.find_elements_by_xpath("//span[text()='Est.']")
links = driver.find_elements_by_class_name("styles-module__link___12BPT")
lat = []
longi = []
driver2 = webdriver.Chrome(executable_path = chromedriver)
for i in links:
    driver2.get(i.get_attribute("href"))
    lat.append(driver2.find_element_by_partial_link_text("Direction").get_attribute("href").split("/")[-1].split("?")[0].split(",")[0])
    longi.append(driver2.find_element_by_partial_link_text("Direction").get_attribute("href").split("/")[-1].split("?")[0].split(",")[1])
    # print(lat[-1], longi[-1])
json_f = []
for i in range(len(names)):
    json_f.append({"name":names[i].text[5:].strip(), "difficulty":difficulty[i].text, "rating":rating[i].get_attribute("aria-label"), "latitude":float(lat[i]), "longitude":float(longi[i]), "length":length[i].text[8:], "ttc":time[i].text[5:]})
    # json_f += f'{{ "name":"{names[i].text[5:].strip()}", "difficulty":"{difficulty[i].text}", "rating":"{rating[i].get_attribute("aria-label")}", "location":"{city}", "length":"{length[i].text[8:]}", "ttc":"{time[i].text[5:]}" }}, '
    # print(names[i].text[5:].strip(), difficulty[i].text, rating[i].get_attribute("aria-label"), city, length[i].text, time[i].text,  sep = "; ")
# json_f = json_f[:-2]
# json_f += ']'
print(json_f)
# out_file = open("irvine_data.txt", "w")
# out_file.write(json_f)
# out_file.close()
client = MongoClient("mongodb+srv://Rio:rsannud1@cluster0.huapd.mongodb.net/CS125_test1?retryWrites=true&w=majority")
db = client.CS125_test1
db.Trails.insert_many(json_f)
print("############done##############")