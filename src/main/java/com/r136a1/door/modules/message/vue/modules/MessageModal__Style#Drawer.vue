<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="消息标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'messageTitle', validatorRules.messageTitle]" placeholder="请输入消息标题"></a-input>
        </a-form-item>
        <a-form-item label="消息内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'messageContent', validatorRules.messageContent]" placeholder="请输入消息内容"></a-input>
        </a-form-item>
        <a-form-item label="发送者id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sendUserId', validatorRules.sendUserId]" placeholder="请输入发送者id"></a-input>
        </a-form-item>
        <a-form-item label="接收者id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'receiveUserId', validatorRules.receiveUserId]" placeholder="请输入接收者id"></a-input>
        </a-form-item>
        <a-form-item label="1:已读 0:未读" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'isRead', validatorRules.isRead]" placeholder="请输入1:已读 0:未读" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="消息状态（0：正常，1：撤回，2：删除）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'status', validatorRules.status]" placeholder="请输入消息状态（0：正常，1：撤回，2：删除）" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="约定发送时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择约定发送时间" v-decorator="[ 'sendTime', validatorRules.sendTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "MessageModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          messageTitle: {rules: [
          ]},
          messageContent: {rules: [
          ]},
          sendUserId: {rules: [
          ]},
          receiveUserId: {rules: [
          ]},
          isRead: {rules: [
          ]},
          status: {rules: [
          ]},
          sendTime: {rules: [
          ]},
        },
        url: {
          add: "/message/message/add",
          edit: "/message/message/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'messageTitle','messageContent','sendUserId','receiveUserId','isRead','status','sendTime'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'messageTitle','messageContent','sendUserId','receiveUserId','isRead','status','sendTime'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>