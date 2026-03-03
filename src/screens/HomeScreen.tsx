import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  Image,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RootStackParamList } from '../types';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Home'>;
};

export default function HomeScreen({ navigation }: Props) {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.content}>
        <View style={styles.headerArea}>
          <Text style={styles.emoji}>🤒</Text>
          <Text style={styles.appTitle}>KindKrank</Text>
          <Text style={styles.tagline}>
            Schritt für Schritt durch die{'\n'}Kinderkrankheit
          </Text>
        </View>

        <View style={styles.infoBox}>
          <Text style={styles.infoText}>
            Ihr Kind ist krank und Sie müssen zu Hause bleiben? Diese App führt Sie
            durch den{' '}
            <Text style={styles.bold}>offiziellen Weg</Text> – von der Bescheinigung
            beim Arzt bis zur Beantragung des Kinderkrankengeldes bei Ihrer
            Krankenkasse.
          </Text>
        </View>

        <TouchableOpacity
          style={styles.mainButton}
          onPress={() => navigation.navigate('Step1FamilySituation')}
          accessibilityLabel="Mein Kind ist krank – Wizard starten"
        >
          <Text style={styles.mainButtonEmoji}>🏥</Text>
          <Text style={styles.mainButtonText}>Mein Kind ist krank</Text>
          <Text style={styles.mainButtonSub}>Jetzt Schritt für Schritt vorgehen</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.secondaryButton}
          onPress={() => navigation.navigate('SpecialCases')}
          accessibilityLabel="Sonderfälle anzeigen"
        >
          <Text style={styles.secondaryButtonText}>ℹ️  Sonderfälle &amp; Ausnahmen</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E6F4FE',
  },
  content: {
    flex: 1,
    padding: 24,
    justifyContent: 'center',
  },
  headerArea: {
    alignItems: 'center',
    marginBottom: 28,
  },
  emoji: {
    fontSize: 70,
    marginBottom: 8,
  },
  appTitle: {
    fontSize: 34,
    fontWeight: 'bold',
    color: '#1A5276',
    letterSpacing: 1,
  },
  tagline: {
    fontSize: 16,
    color: '#555',
    textAlign: 'center',
    marginTop: 6,
    lineHeight: 22,
  },
  infoBox: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 16,
    marginBottom: 28,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
  },
  infoText: {
    fontSize: 15,
    color: '#444',
    lineHeight: 23,
    textAlign: 'center',
  },
  bold: {
    fontWeight: 'bold',
    color: '#1A5276',
  },
  mainButton: {
    backgroundColor: '#2E86AB',
    borderRadius: 16,
    paddingVertical: 22,
    paddingHorizontal: 20,
    alignItems: 'center',
    marginBottom: 14,
    elevation: 4,
    shadowColor: '#2E86AB',
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.3,
    shadowRadius: 6,
  },
  mainButtonEmoji: {
    fontSize: 36,
    marginBottom: 6,
  },
  mainButtonText: {
    color: '#fff',
    fontSize: 22,
    fontWeight: 'bold',
  },
  mainButtonSub: {
    color: 'rgba(255,255,255,0.85)',
    fontSize: 14,
    marginTop: 4,
  },
  secondaryButton: {
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
    borderWidth: 1.5,
    borderColor: '#2E86AB',
    backgroundColor: '#fff',
  },
  secondaryButtonText: {
    color: '#2E86AB',
    fontSize: 15,
    fontWeight: '600',
  },
});
