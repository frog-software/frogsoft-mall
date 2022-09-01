import json
import jieba
import pickle
from fastapi import FastAPI
from pydantic import BaseModel


class Query(BaseModel):
    chatRecord:list


# path
# 数据保存路径
qa_path = './data/test2.json'
tv_path = './data/tv2.pkl'
cp_path = './data/cp2.pkl'

# retrieve qa, tv and cp built in gen.pysparnn
# 加载之前保存的数据
qa = json.load(open(qa_path))
tv = pickle.load(open(tv_path, 'rb'))
cp = pickle.load(open(cp_path, 'rb'))


app = FastAPI()


@app.post("/")
def read_root(query:Query):
    # get the question
    # 获取用户的提问
    question = query.chatRecord[len(query.chatRecord)-1]

    # dived question into words
    # 分词
    cutted_qustion = jieba.cut(question)
    cutted_qustion = ' '.join(cutted_qustion)

    # construct search data
    # 构造搜索数据
    search_data = [cutted_qustion]
    search_tfidf = tv.transform(search_data)

    # search from cp, k is the number of matched qa that you need
    # 搜索数据，会获取到前 k 个匹配的 QA
    result_array = cp.search(search_tfidf, k=2, k_clusters=2, return_distance=False)
    ans="暂时不能理解您的问题呢~"
    for index in range(len(result_array)):
        if index >= 10:
            break
        result = result_array[index]
        if len(result):
            ans=qa[int(result[0])]['a']

    return { "data": {"chatForNow":ans} }


