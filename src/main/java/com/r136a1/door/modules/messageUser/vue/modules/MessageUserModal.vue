<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="用户头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'userAvator', validatorRules.userAvator]" placeholder="请输入用户头像"></a-input>
        </a-form-item>
        <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'userName', validatorRules.userName]" placeholder="请输入用户名"></a-input>
        </a-form-item>
        <a-form-item label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'userPassword', validatorRules.userPassword]" placeholder="请输入密码"></a-input>
        </a-form-item>
        <a-form-item label="登录次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'loginTimes', validatorRules.loginTimes]" placeholder="请输入登录次数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="用户等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'level', validatorRules.level]" placeholder="请输入用户等级" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'age', validatorRules.age]" placeholder="请输入年龄"></a-input>
        </a-form-item>
        <a-form-item label="年龄" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'age', validatorRules.age]" placeholder="请输入年龄"></a-input>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sex', validatorRules.sex]" placeholder="请输入性别" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sex', validatorRules.sex]" placeholder="请输入性别" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
        </a-form-item>
        <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'location', validatorRules.location]" placeholder="请输入位置"></a-input>
        </a-form-item>
        <a-form-item label="背景图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'backgroundImg', validatorRules.backgroundImg]" placeholder="请输入背景图片"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "MessageUserModal",
    components: { 
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
          userAvator: {rules: [
          ]},
          userName: {rules: [
          ]},
          userPassword: {rules: [
          ]},
          loginTimes: {rules: [
          ]},
          level: {rules: [
          ]},
          age: {rules: [
          ]},
          age: {rules: [
          ]},
          sex: {rules: [
          ]},
          sex: {rules: [
          ]},
          location: {rules: [
          ]},
          location: {rules: [
          ]},
          backgroundImg: {rules: [
          ]},
        },
        url: {
          add: "/messageUser/messageUser/add",
          edit: "/messageUser/messageUser/edit",
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
          this.form.setFieldsValue(pick(this.model,'userAvator','userName','userPassword','loginTimes','level','age','age','sex','sex','location','location','backgroundImg'))
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
        this.form.setFieldsValue(pick(row,'userAvator','userName','userPassword','loginTimes','level','age','age','sex','sex','location','location','backgroundImg'))
      },

      
    }
  }
</script>