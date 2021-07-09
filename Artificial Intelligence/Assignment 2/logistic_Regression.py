# read all files in training dataset
import glob
import re
import os
import math
import random


# you read percentage of data based on input(ex. x=10%,x=20%,...,x=100%)
def readFile(x):
    pos_dict = {}
    list = ()
    pos_string = ''
    counter = 0
    dir = 'pos/'
    listoffiles = os.listdir(dir)  # dir is your directory path
    number_files = len(listoffiles)
    print('number of files in positive')
    print(number_files)
    for name in glob.glob("pos\*"):
        if (counter == x * number_files / 100):
            break
        f = open(name, encoding="utf8")
        pos_string = pos_string + f.read()
        counter += 1
    dir = 'neg/'
    listoffiles = os.listdir(dir)
    number_files = len(listoffiles)
    counter == 0
    for name in glob.glob("neg\*"):
        if (counter == x * number_files / 100):
            break
        f = open(name, encoding="utf8")
        pos_string = pos_string + f.read()
        counter += 1

    list = re.findall(r"[\w']+", pos_string)
    for i in list:
        if i in pos_dict:
            pos_dict[i] += 1
        else:
            pos_dict[i] = 1
    return pos_dict


# you give as input the percentage of training data you want the algorithm to be trained of and return a dictionary with the most frequent words

def training_data(x):
    pos_dict = readFile(x)


    # pos_dict=sorted(pos_dict.items(),reverse=False)
    #we make the dictionary with the word frequency
    pos_dict = {k: v for k, v in sorted(pos_dict.items(), key=lambda item: item[1], reverse=True)}
    print('made the dictionary with the word frequency')
    #we make the vocabulary choosing m words not including n most frequent words
    vocabulary = makeVocabulary(300,50,pos_dict)
    print('made the vocabulary choosing m words')
    #we read every positive file and we make the vector
    list_pos = makeVector(vocabulary,x,"pos")
    print('made positive vector')
    #we read every negative file and me make the vector
    list_neg=makeVector(vocabulary,x,"neg")
    print('made negative vector')


   # for i in vocabulary:
   #     ig_vector[i] = informationGain(list_pos, list_neg, 1, -1, i)
   # ig_vector = {k: v for k, v in sorted(ig_vector.items(), key=lambda item: item[1], reverse=True)}
    #s = 20
    #ig_vector= {k: ig_vector[k] for k in list(ig_vector)[:s]}







   # list_w=[]
   # listpos=finalVector(list_pos,ig_vector)
    #listneg=finalVector(list_neg,ig_vector)


    ig_vector={}
    #we compute information gain for every word in our vector and we choose s best words



    w = w_initialization(list_pos)
    print('w initialization')
    w=update_w(w,list_pos,list_neg,0.01,0.01)



   # print(w)
    return_list=[]
    return_list.append(list_pos[0])
    return_list.append(w)
    print('w is ready')

    return return_list








#    return list_pos


# print(pos_string)
# print(list)
def makeVocabulary(m, n, pos_dict):
    vocabulary = []
    counter1 = 0
    counter2 = 0

    for word in pos_dict:
        if (counter1 > n):
            if (counter2 > m):
                break
            vocabulary.append(word)
            counter2 += 1
        else:
            counter1 += 1
    return vocabulary


def makeVector(vocabulary, x,dir):
    l_vectors = []
    # read every positive review and make a list of dictionaries with words as keys and values that are either 0(not exists) or 1(exists)
    pos_string = ''
    counter = 0

    listoffiles = os.listdir(dir)  # dir is your directory path
    number_files = len(listoffiles)
    dir=dir+"\*"
    for name in glob.glob(dir):
        vector = {}
        if (counter == x * number_files / 100):
            break
        f = open(name, encoding="utf8")
        pos_string = f.read()
        list = re.findall(r"[\w']+", pos_string)
        for wordVoc in vocabulary:
            for wordlist in list:
                if (wordVoc == wordlist):
                    vector[wordVoc] = 1
                    break
                else:
                    vector[wordVoc] =0
        l_vectors.append(vector)
        counter += 1
    return l_vectors


# H(C)=1
#computes P(X=x|C=c)
def prop_xc(listdict,key,value):
    counter=0
    total=len(listdict)
    for i in listdict:
        vector=i
        if vector[key]==value:
            counter+=1
    return counter/total



# computes P(C=c|X=x)
def prop_cx(listdict,key,value):
    prop=prop_xc(listdict,key,value)
    return prop



# Computes H(C=c|X=x)
def entropy(listpos,listneg,key,value):
    prop=-prop_cx(listpos,key,value)*log2(prop_cx(listpos,key,value))-prop_cx(listneg,key,value)*log2(prop_cx(listneg,key,value))
    return prop




def log2(Base):
    return math.log(Base+1,2)


# Computes IG(X,C)
def informationGain(listpos,listneg,value1,value2,key):
    ig=1-(1/2)*entropy(listpos,listneg,key,value1)-(1/2)*entropy(listpos,listneg,key,value2)
    return ig



def finalVector(list_vec, ig_vector):
    vector = {}
    finalVector = []
    vectors = list_vec
    for i in vectors:
        vector = i.copy()
        for j in i:
            if j not in ig_vector:
                del vector[j]
        finalVector.append(vector)

    return finalVector


# 𝑤𝑙 ← (1 − 2 ∙ 𝜆 ∙ 𝜂) ∙ 𝑤𝑙 + 𝜂 ∙[ 𝑦(𝑖) − 𝑃(𝑐+|𝑥(𝑖))]𝑥𝑙(𝑖)
def update_w(w,list_pos,list_neg,η,λ):


  for i,j in zip(list_pos,list_neg):
    x=i
    y=j

    for (kw, vw) in w.items():
        yhat = prop_cx_vector(w, x)
        if kw == '0':
            w[kw] =(1 - 2 * λ * η) * w[kw] + η * (1- yhat) * 1
        else:
            w[kw] =(1 - 2 * λ * η) * w[kw] + η * (1- yhat) * x[kw]

    for(kw,vw) in w.items():
        yhat = prop_cx_vector(w, y)
        if kw == '0':
            w[kw] = (1 - 2 * λ * η) * w[kw] + η * (0- yhat) * 1
        else:
            w[kw] = (1 - 2 * λ * η) * w[kw] + η * (0-yhat) * y[kw]




  return w






def prop_cx_vector(w, x):
    return 1.0/(1.0 + math.exp(-w_x(w,x)))




def update_w_mb(w,listclass,η,λ,y):

    vector={}
    for (kw, vw) in w.items():
        print(listclass)
        sum = 0
        for i in listclass:
            vector=i
            if kw == '0':
                sum+=(y - prop_cx_vector(w, i)) * 1
            else:
                sum+=(y - prop_cx_vector(w, i)) * vector[kw]
        w[kw] = (1 - 2 * λ * η) * w[kw] + η *sum
    return w

def w_x(w, x):
    list_x = []
    list_w = []
    for (kw, vw) in w.items():
        list_w.append(vw)

    for (kx, vx) in x.items():
        list_x.append(vx)

    f = list_w[0]

    for i in range(0, len(list_x)):
        f += list_w[i + 1] * list_x[i]

    return f


def w_initialization(vector):
    w = {}
    w['0']=random.uniform(0,1)

    x = vector[0]
    for i in x:
     w[i]=random.uniform(0,1)

    return w








def precision(TP,FP):
    return TP/(TP+FP)


def recall(TP,FN):
    return TP /(TP + FN)

def f1_measure(precision,recall):
    return 2*precision*recall/(precision+recall)



def test_data(x,w,ig_vector,dir_pos,dir_neg):
    l_vectors={}

    l_vectors_pos=makeVector(ig_vector,x,dir_pos)
    l_vectors_neg=makeVector(ig_vector, x,dir_neg)
    counter=0

    TP=0
    FP=0
    TN=0
    FN=0
    for i in l_vectors_pos:
        if prop_cx_vector(w,i)>0.5:
            TP+=1
        else:
            FN+=1
        counter += 1




    print((l_vectors_pos[0]))
    counter=0
    for i in l_vectors_neg:
        if 1-prop_cx_vector(w,i)>0.5:
            TN+=1
        else:
            FP+=1
        counter += 1





    print('TP= '+str(TP))
    print('TN= ' + str(TN))
    print('FP= '+str(FP))
    print('FN= '+str(FN))



    accuracy=(TP+TN)/(TP+TN+FP+FN)
    dev_loss=1-accuracy
    print('accuracy= '+str(accuracy))
    print('dev loss= '+str(dev_loss))


def compute_PR(x,w,ig_vector,dir_pos,dir_neg,thresh):
    l_vectors_pos = makeVector(ig_vector, x, dir_pos)
    l_vectors_neg = makeVector(ig_vector, x, dir_neg)
    counter = 0

    TP = 0
    FP = 0
    TN = 0
    FN = 0
    for i in l_vectors_pos:
        if prop_cx_vector(w, i) > thresh:
            TP += 1
        else:
            FN += 1
        counter += 1

    print((l_vectors_pos[0]))
    counter = 0
    for i in l_vectors_neg:
        if 1-prop_cx_vector(w, i) > thresh:
            TN += 1
        else:
            FP += 1
        counter += 1

    precision_pos=TP/(TP+FP)
    precision_neg=TN/(TN+FN)

    if TP+FN==0:
        recall_pos=0
    else:
        recall_pos = TP / (TP + FN)

    if TN+FP==0:
        recall_neg=0
    else:
        recall_neg = TN / (TN + FP)

    recall=(recall_neg+recall_pos)/2
    precision=(precision_pos+precision_neg)/2

    f1_score=2*(precision*recall)/(precision+recall)

    print('for threshold= '+str(thresh))
    print('precision= '+str(precision))
    print('recall= ' + str(recall))
    print('f1_score= '+str(f1_score))
    print('------------------------------------------------')




dir_test_pos='test_pos'
dir_test_neg='test_neg'
dir_train_pos='pos'
dir_train_neg='neg'
print('OF TRAINING')



#for i in range(10,110,10):
#return_list = training_data(i)
  #  test_data(i,return_list[1],return_list[0],dir_train_pos,dir_train_neg)
   # test_data(100,return_list[1],return_list[0],dir_test_pos,dir_test_neg)


#for threshold
#for i in range(0,11,1):
 #   compute_PR(100, return_list[1], return_list[0], dir_test_pos, dir_test_neg,i/10)







